package com.github.amitsureshchandra.onlinecompiler.service.core;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecompiler.exception.ServerException;
import com.github.amitsureshchandra.onlinecompiler.service.lang.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
@Slf4j
public class RunnerService {
    private final CommonLangService commonLangService;

    public RunnerService(CommonLangService commonLangService) {
        this.commonLangService = commonLangService;
    }

    public OutputResp run(CodeReqDto dto) {
        try {
            var outputResp = runPrivate(dto);
            if(outputResp.exitCode() == 0) return outputResp;
            log.error(outputResp.toString());
            throw new ServerException("Server Error");
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
            throw new ServerException("Server Error");
        }
    }

    private OutputResp runPrivate(CodeReqDto dto) throws IOException, InterruptedException {
        return commonLangService.run(dto);
    }
}
