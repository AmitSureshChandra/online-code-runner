package com.github.amitsureshchandra.onlinecoderunner.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ParseUtil {
    final ObjectMapper objectMapper;

    public ParseUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T>T parseFromString(String s, Class<T> classT) {
        try {
            return objectMapper.readValue(s.getBytes(), classT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public <T>T parseFromString(byte[] s, Class<T> classT) {
        try {
            return objectMapper.readValue(s, classT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public <T>String parseToString(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
