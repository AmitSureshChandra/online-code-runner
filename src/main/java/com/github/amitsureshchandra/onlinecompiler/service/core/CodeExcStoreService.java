package com.github.amitsureshchandra.onlinecompiler.service.core;

import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CodeExcStoreService {
    private Map<String, OutputResp> codeMap = new HashMap<>();
    public void store(String k, OutputResp outputResp) {
        codeMap.put(k, outputResp);
        log.info("output for key " + k + " has been stored");
    }

    public OutputResp get(String key) {
        return codeMap.get(key);
    }

    public boolean checkKeyProcessed(String key) {
        return codeMap.containsKey(key);
    }
}
