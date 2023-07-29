package com.github.amitsureshchandra.onlinecompiler.service.core;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecompiler.exception.ServerException;
import com.github.amitsureshchandra.onlinecompiler.exception.ValidationException;
import com.github.amitsureshchandra.onlinecompiler.service.lang.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
@Slf4j
public class RunnerService {

    final JavaLangService javaLangService;
    final GoLangService goLangService;
    final PythonLangService pythonLangService;
    final JavascriptLangService javascriptLangService;
    final Cplus2Langservice cplus2Langservice;

    public RunnerService(JavaLangService javaLangService, GoLangService goLangService, PythonLangService pythonLangService,
                         JavascriptLangService javascriptLangService,Cplus2Langservice cplus2Langservice) {
        this.javaLangService = javaLangService;
        this.goLangService = goLangService;
        this.pythonLangService= pythonLangService;
        this.javascriptLangService = javascriptLangService;
        this.cplus2Langservice = cplus2Langservice;
    }

    public OutputResp run(CodeReqDto dto) {
        OutputResp outputResp = null;
        try {
            outputResp = runPrivate(dto);
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        if(Arrays.asList(0,1).contains(outputResp.getExitCode())) return outputResp;
        log.error(outputResp.toString());
        throw new ServerException("Server Error");
    }

    private OutputResp runPrivate(CodeReqDto dto) throws IOException, InterruptedException {
        switch (dto.getCompiler()) {
            case "jdk8":
            case "jdk20":
                return javaLangService.run(dto);
            case "golang12":
                return goLangService.run(dto);
            case "python3":
                return pythonLangService.run(dto);
            case "javascript":
                return javascriptLangService.run(dto);
            case "c/c++":
                return cplus2Langservice.run(dto);
            default:
                throw  new ValidationException("compiler not found");
        }
    }
}
