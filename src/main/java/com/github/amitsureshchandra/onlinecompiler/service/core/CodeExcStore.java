package com.github.amitsureshchandra.onlinecompiler.service.core;

import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecompiler.service.util.ParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CodeExcStore extends CodeExcStoreImpl {
    private final RedisTemplate<String, Object> redisTemplate;

    final ParseUtil parseUtil;

    public CodeExcStore(RedisTemplate<String, Object> redisTemplate, ParseUtil parseUtil) {
        this.redisTemplate = redisTemplate;
        this.parseUtil = parseUtil;
    }

    public void store(String k, OutputResp outputResp) {
        redisTemplate.opsForHash().put(getHashKey(), k, parseUtil.parseToString(outputResp));
        log.info("Stored data in Redis. Key: {}, Value: {}", k, parseUtil.parseToString(outputResp));
    }

    public OutputResp get(String key) {
        Object obj = redisTemplate.opsForHash().get(getHashKey(), key);
        if(obj == null) return null;
        return parseUtil.parseFromString(obj.toString(), OutputResp.class);
    }

    public boolean checkKeyProcessed(String key) {
        return redisTemplate.opsForHash().hasKey(getHashKey(), key);
    }
}
