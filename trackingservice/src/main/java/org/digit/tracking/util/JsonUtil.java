package org.digit.tracking.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtil {
    public static String getJsonFromObject(Object obj){
        ObjectMapper converter = new ObjectMapper();
        converter.registerModule(new JavaTimeModule());
        try {
            return converter.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
