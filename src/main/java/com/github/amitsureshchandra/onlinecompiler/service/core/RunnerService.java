package com.github.amitsureshchandra.onlinecompiler.service.core;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecompiler.exception.ValidationException;
import com.github.amitsureshchandra.onlinecompiler.service.lang.GoLangService;
import com.github.amitsureshchandra.onlinecompiler.service.lang.JavaLangService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class RunnerService {

    final JavaLangService javaLangService;
    final GoLangService goLangService;

    public RunnerService(JavaLangService javaLangService, GoLangService goLangService) {
        this.javaLangService = javaLangService;
        this.goLangService = goLangService;
    }

    public OutputResp run(CodeReqDto dto) throws IOException, InterruptedException {
        OutputResp outputResp = runPrivate(dto);
        if(outputResp.getExitCode() == 0) return outputResp;
        log.error(outputResp.toString());
        throw new RuntimeException("Server Error");
    }

    private OutputResp runPrivate(CodeReqDto dto) throws IOException, InterruptedException {
        switch (dto.getCompiler()) {
            case "jdk8":
            case "jdk20":
                return javaLangService.run(dto);
            case "golang12":
                return goLangService.run(dto);
            default:
                throw  new ValidationException("compiler not found");
        }
    }
}
