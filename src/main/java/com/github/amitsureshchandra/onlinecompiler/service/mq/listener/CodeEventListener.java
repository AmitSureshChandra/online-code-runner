package com.github.amitsureshchandra.onlinecompiler.service.mq.listener;

import com.github.amitsureshchandra.onlinecompiler.config.MQConfig;
import com.github.amitsureshchandra.onlinecompiler.dto.event.CodeEventDto;
import com.github.amitsureshchandra.onlinecompiler.service.mq.processor.CodeEventProcessor;
import com.github.amitsureshchandra.onlinecompiler.service.util.ParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
public class CodeEventListener implements IEventListener{

    final
    MessageConverter msgConverter;

    final
    CodeEventProcessor codeEventProcessor;

    final
    ParseUtil parseUtil;

    private CountDownLatch latch = new CountDownLatch(1);

    public CodeEventListener(MessageConverter msgConverter, CodeEventProcessor codeEventProcessor, ParseUtil parseUtil) {
        this.msgConverter = msgConverter;
        this.codeEventProcessor = codeEventProcessor;
        this.parseUtil = parseUtil;
    }

    @Override
    @RabbitListener(queues = {MQConfig.queueName})
    public void listenCodeEvent(@Payload String dto) {
        log.info("event dto : {}", dto);
        codeEventProcessor.process(parseUtil.parseFromString(dto, CodeEventDto.class));
        latch.countDown();
    }


    public CountDownLatch getLatch() {
        return latch;
    }
}
