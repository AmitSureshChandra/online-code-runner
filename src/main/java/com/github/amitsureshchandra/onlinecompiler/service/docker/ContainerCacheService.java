package com.github.amitsureshchandra.onlinecompiler.service.docker;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ContainerCacheService {
    Map<String, String> cachedContainerMap = new HashMap<>();

    public String addContainer(String value) {
        String key = getKey(value);
        cachedContainerMap.put(key, value);
        return key;
    }

    private String getKey(String value) {
        return UUID.randomUUID().toString();
    }
}
