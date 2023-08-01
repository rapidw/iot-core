package io.rapidw.iotcore.api.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class StringUtil {

    private static final int KEY_LENGTH = 12;

    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-","").substring(0,15)+System.currentTimeMillis();
    }

    public static String generateKey(){
        return RandomStringUtils.randomAlphanumeric(KEY_LENGTH);
    }

}
