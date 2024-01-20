package com.github.amitsureshchandra.onlinecompiler.service.mq.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.amitsureshchandra.onlinecompiler.config.MQConfig;
import com.github.amitsureshchandra.onlinecompiler.dto.event.CodeEventDto;
import com.github.amitsureshchandra.onlinecompiler.service.mq.processor.CodeEventProcessor;
import com.github.amitsureshchandra.onlinecompiler.service.util.ParseUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Component
public class CodeEventListener {

    @Autowired
    MessageConverter msgConverter;

    @Autowired
    CodeEventProcessor codeEventProcessor;

    @Autowired
    ParseUtil parseUtil;

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    ObjectMapper objectMapper;

    @RabbitListener(queues = MQConfig.queueName)
    void listenCodeEvent(@Payload Message dto) {
        codeEventProcessor.process(parseUtil.parseFromString(dto.getBody(), CodeEventDto.class));
        latch.countDown();
    }


    public CountDownLatch getLatch() {
        return latch;
    }
}
