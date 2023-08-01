package io.rapidw.iotcore.api.service;

import io.rapidw.iotcore.api.config.ConnectorConfig;
import io.rapidw.iotcore.api.request.ServiceCallRequest;
import io.rapidw.iotcore.api.response.ServiceCallResponse;
import io.rapidw.iotcore.common.exception.AppException;
import io.rapidw.iotcore.common.exception.AppStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CallServiceService {

    private final RestTemplate restTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final ConnectorConfig connectorConfig;

    private static final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("device-command-pool-%d").build();
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2,100,
            TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(10), namedThreadFactory,new ThreadPoolExecutor.CallerRunsPolicy());

    public CallServiceService(RestTemplate restTemplate, StringRedisTemplate stringRedisTemplate, ConnectorConfig connectorConfig) {
        this.restTemplate = restTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.connectorConfig = connectorConfig;
    }

    public String callService(String productId, String deviceName, String functionId, String request) {
        //从redis获取设备连接
        String address = (String) stringRedisTemplate.opsForHash().get(productId, deviceName);
//        String address = "10.233.103.34";
        if (null == address) {
            throw new AppException(AppStatus.NOT_FOUND, "未获取到mqtt连接！");
        }
        String url = "http://" + address + ":" + connectorConfig.getCommandUrlSuffix();

        //调用connector
        Map<String, String> params = new HashMap<>(4);
        params.put("productId", productId);
        params.put("deviceName", deviceName);
        params.put("functionId", functionId);
        String response = restTemplate.postForObject(url, request, String.class, params);
        return response;
    }

    public List<ServiceCallResponse> callService(String productId, String functionId, List<ServiceCallRequest> serviceCallRequests) throws InterruptedException, JsonProcessingException {

        Map<String,List<ServiceCallRequest>> map = serviceCallRequests.stream().collect(Collectors.groupingBy(ServiceCallRequest::getDeviceName,
                LinkedHashMap::new, Collectors.toCollection(ArrayList::new)));
        int count = map.size();
        List<ServiceCallResponse> result = new ArrayList<>(count);
        CountDownLatch countDownLatch = new CountDownLatch(count);
        Map<String, String> params = new HashMap<>(4);
        params.put("productId", productId);
        params.put("functionId", functionId);
        map.forEach((key, value) -> {
            EXECUTOR_SERVICE.submit(() -> {
                ServiceCallResponse serviceCallResponse  = new ServiceCallResponse();
                serviceCallResponse.setDeviceName(key);
                value.forEach(s->{
                    log.debug("deviceName:{} command:{}",s.getDeviceName(),s.getCommand());
                    String address = (String) stringRedisTemplate.opsForHash().get(productId, s.getDeviceName());
                    if (null == address) {
                        log.error("deviceName={} cannot get mqtt connect!",s.getDeviceName());
                        serviceCallResponse.setResponse("未获取到设备连接!");
                        serviceCallResponse.setCode(-1);
                        return;
                    }else{
                        try{
                            params.put("deviceName", s.getDeviceName());serviceCallResponse.setDeviceName(s.getDeviceName());
                            serviceCallResponse.setResponse(restTemplate.postForObject("http://" + address + ":" +
                                    connectorConfig.getCommandUrlSuffix(), s.getCommand(), String.class, params));
                            log.debug("deviceName:{} execute finish !",s.getDeviceName());
                        }catch (Exception e){
                            log.error("deviceName={} Execute command failure！",s.getDeviceName());
                            e.printStackTrace();
                            serviceCallResponse.setResponse("未知错误！");
                            return;
                        }
                    }
                });
                result.add(serviceCallResponse);
                countDownLatch.countDown();
            });

        });
        countDownLatch.await();
        log.debug("execute finish!");
        ObjectMapper o = new ObjectMapper();
        log.debug("response:{}",o.writeValueAsString(result));
        return  result;
    }
}
