package com.github.amitsureshchandra.onlinecompiler.service.mq.listener;

import com.github.amitsureshchandra.onlinecompiler.config.MQConfig;
import com.github.amitsureshchandra.onlinecompiler.dto.event.CodeEventDto;
import com.github.amitsureshchandra.onlinecompiler.service.mq.processor.CodeEventProcessor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class CodeEventListener {

    @Autowired
    MessageConverter msgConverter;

    @Autowired
    CodeEventProcessor codeEventProcessor;

    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener(queues = MQConfig.queueName)
    void listenCodeEvent(@Payload Message msg) {
        codeEventProcessor.process((CodeEventDto) msgConverter.fromMessage(msg));
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
