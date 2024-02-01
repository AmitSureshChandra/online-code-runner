package com.github.amitsureshchandra.onlinecompiler.service.core;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecompiler.exception.ValidationException;
import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

public interface IRunnerService {
    Map<String, String> fileNameMap = new HashMap<>();

    @PostConstruct
    default void init() {
        fileNameMap.put("jdk", "Solution.java");
        fileNameMap.put("golang12", "main.cpp");
        fileNameMap.put("python3", "app.js");
        fileNameMap.put("node20", "main.go");
        fileNameMap.put("gcc11", "solution.py");
    }

    /**
     * takes req dto then stores code in a temp directory & returns name of directory
     * @param codeReqDto
     * @return directory name that contains code
     */
    String storeCode(CodeReqDto codeReqDto);

    /**
     * get filename with extension
     * @param compiler
     * @return filename
     */
    default String getFileName(String compiler) {
        if(!fileNameMap.containsKey(compiler)) throw new ValidationException("compiler not found");
        return fileNameMap.get(compiler);
    }

    OutputResp runCode(CodeReqDto codeReqDto);
    String runCodeAsync(CodeReqDto codeReqDto);
    String getCodeExcStatus(String excId);
    void postCleanUp(String userFolder, String containerId);
}
