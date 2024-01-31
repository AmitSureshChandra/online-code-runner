package com.github.amitsureshchandra.onlinecompiler.service.core;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecompiler.exception.ServerException;
import com.github.amitsureshchandra.onlinecompiler.exception.ValidationException;
import com.github.amitsureshchandra.onlinecompiler.service.lang.CommonLangService;
import com.github.amitsureshchandra.onlinecompiler.service.util.ParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class RunnerService {
    private final CommonLangService commonLangService;

    public RunnerService(RabbitTemplate rabbitTemplate, ModelMapper modelMapper, CommonLangService commonLangService, ParseUtil parseUtil) {
        this.commonLangService = commonLangService;
    }

    public OutputResp run(CodeReqDto dto) {
        try {
            var outputResp = runPrivate(dto);
            if(outputResp.exitCode() == 0) return outputResp;
            log.error(outputResp.toString());
            throw new ValidationException("Some error occurred");
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
            throw new ServerException("Server Error");
        }
    }

    private OutputResp runPrivate(CodeReqDto dto) throws IOException, InterruptedException {
        return commonLangService.run(dto);
    }
}
