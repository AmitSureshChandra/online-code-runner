package com.github.amitsureshchandra.onlinecoderunner.service.mq.processor;

import com.github.amitsureshchandra.onlinecoderunner.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecoderunner.dto.event.CodeEventDto;
import com.github.amitsureshchandra.onlinecoderunner.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecoderunner.service.core.CodeExcStore;
import com.github.amitsureshchandra.onlinecoderunner.service.core.IRunnerService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CodeEventProcessor implements IEventProcessor<CodeEventDto> {

    final IRunnerService IRunnerService;
    final ModelMapper modelMapper;

    final CodeExcStore codeExcStore;

    final RabbitTemplate rabbitTemplate;
    private final RedisTemplate<String, Object> redisTemplate;


    public CodeEventProcessor(IRunnerService IRunnerService, ModelMapper modelMapper, CodeExcStore codeExcStore, RabbitTemplate rabbitTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.IRunnerService = IRunnerService;
        this.modelMapper = modelMapper;
        this.codeExcStore = codeExcStore;
        this.rabbitTemplate = rabbitTemplate;
        this.redisTemplate = redisTemplate;
    }

    public void process(CodeEventDto dto) {
        log.info(String.valueOf(dto));

        // run code
        OutputResp outputResp = IRunnerService.runCode(modelMapper.map(dto, CodeReqDto.class));

        // adding on redis
        codeExcStore.store(dto.getId(), outputResp);
    }
}
