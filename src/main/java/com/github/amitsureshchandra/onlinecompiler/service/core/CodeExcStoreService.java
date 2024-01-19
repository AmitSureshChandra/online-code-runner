package com.github.amitsureshchandra.onlinecompiler.service.core;

import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CodeExcStoreService {
    private final RedisTemplate<String, Object> redisTemplate;
    private String OUTPUT_KEY = "output";

    public CodeExcStoreService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void store(String k, OutputResp outputResp) {
        redisTemplate.opsForHash().put(OUTPUT_KEY, k, outputResp);
        log.info("Stored data in Redis. Key: {}, Value: {}", k, outputResp);
    }

    public OutputResp get(String key) {
        return (OutputResp)redisTemplate.opsForHash().get(OUTPUT_KEY, key);
    }

    public boolean checkKeyProcessed(String key) {
        return redisTemplate.opsForHash().hasKey(OUTPUT_KEY, key);
    }
}
