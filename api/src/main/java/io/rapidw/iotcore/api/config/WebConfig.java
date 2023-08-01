package io.rapidw.iotcore.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToInstantConverter());
        registry.addConverter(new InstantToStringConverter());
    }

    public static class InstantToStringConverter implements Converter<Instant, String> {

        @Override
        public String convert(Instant source) {
            return LocalDateTime.ofInstant(source, ZoneId.systemDefault()).format(FORMATTER);
        }
    }

    public static class StringToInstantConverter implements Converter<String, Instant> {

        @Override
        public Instant convert(String source) {
            return LocalDateTime.parse(source, FORMATTER).toInstant(ZoneOffset.ofHours(8));
        }
    }
}
