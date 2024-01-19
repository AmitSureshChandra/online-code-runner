package com.github.amitsureshchandra.onlinecompiler.service.mq.processor;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.dto.event.CodeEventDto;
import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecompiler.service.core.CodeExcStoreService;
import com.github.amitsureshchandra.onlinecompiler.service.core.RunnerService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CodeEventProcessor {

    final RunnerService runnerService;
    final ModelMapper modelMapper;

    final CodeExcStoreService codeExcStoreService;

    public CodeEventProcessor(RunnerService runnerService, ModelMapper modelMapper, CodeExcStoreService codeExcStoreService) {
        this.runnerService = runnerService;
        this.modelMapper = modelMapper;
        this.codeExcStoreService = codeExcStoreService;
    }

    public void process(CodeEventDto dto) {
        log.info(String.valueOf(dto));

        // run code
        OutputResp outputResp = runnerService.run(modelMapper.map(dto, CodeReqDto.class));

        // store result
        codeExcStoreService.store(dto.getId(), outputResp);
    }
}
