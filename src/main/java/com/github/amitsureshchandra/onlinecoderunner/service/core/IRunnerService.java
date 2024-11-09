package com.github.amitsureshchandra.onlinecoderunner.service.core;

import com.github.amitsureshchandra.onlinecoderunner.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecoderunner.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecoderunner.exception.ValidationException;
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
        fileNameMap.put("input", "input.txt");
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
    OutputResp getOutput(String excId);
    void postCleanUp(String userFolder, String containerId);
}
