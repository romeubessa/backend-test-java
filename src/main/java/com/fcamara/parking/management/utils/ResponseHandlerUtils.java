package com.fcamara.parking.management.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ResponseHandlerUtils {

    public static ResponseEntity<Object> generateResponse(HttpStatus status, Object obj) {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("status", status.value());
        map.put("data", obj);

        return new ResponseEntity<>(map, status);
    }
}
