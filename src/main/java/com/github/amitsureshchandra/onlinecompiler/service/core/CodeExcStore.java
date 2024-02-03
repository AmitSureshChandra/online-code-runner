package com.github.amitsureshchandra.onlinecompiler.service.core;

import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;

public abstract class CodeExcStore {
    protected String OUTPUT_KEY = "output";

    /**
     * store code response with key of excId
     * @param k excId
     * @param outputResp
     */
    abstract public void store(String k, OutputResp outputResp);

    /**
     * get code output with excId
     * @param key
     * @return
     */
    abstract public OutputResp get(String key);

    /**
     * get hashKey
     * @return
     */
    protected String getHashKey() {
        return OUTPUT_KEY;
    }

    public abstract boolean checkKeyProcessed(String excId);
}
