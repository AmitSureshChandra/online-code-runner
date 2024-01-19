package com.github.amitsureshchandra.onlinecompiler.service.mq.processor;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.dto.event.CodeEventDto;
import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecompiler.service.core.CodeExcStoreService;
import com.github.amitsureshchandra.onlinecompiler.service.core.RunnerService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CodeEventProcessor {

    final RunnerService runnerService;
    final ModelMapper modelMapper;

    final CodeExcStoreService codeExcStoreService;

    final RabbitTemplate rabbitTemplate;
    private final RedisTemplate<String, Object> redisTemplate;


    public CodeEventProcessor(RunnerService runnerService, ModelMapper modelMapper, CodeExcStoreService codeExcStoreService, RabbitTemplate rabbitTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.runnerService = runnerService;
        this.modelMapper = modelMapper;
        this.codeExcStoreService = codeExcStoreService;
        this.rabbitTemplate = rabbitTemplate;
        this.redisTemplate = redisTemplate;
    }

    public void process(CodeEventDto dto) {
        log.info(String.valueOf(dto));

        // run code
        OutputResp outputResp = runnerService.run(modelMapper.map(dto, CodeReqDto.class));

        // adding on redis
        codeExcStoreService.store(dto.getId(), outputResp);
    }
}
