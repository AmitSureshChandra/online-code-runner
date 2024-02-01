package com.github.amitsureshchandra.onlinecompiler.unit_tests.controller;

import com.github.amitsureshchandra.onlinecompiler.dto.event.CodeEventDto;
import com.github.amitsureshchandra.onlinecompiler.service.core.CodeExcStore;
import com.github.amitsureshchandra.onlinecompiler.service.mq.listener.CodeEventListener;
import com.github.amitsureshchandra.onlinecompiler.service.mq.processor.CodeEventProcessor;
import com.github.amitsureshchandra.onlinecompiler.service.util.ParseUtil;
import com.github.amitsureshchandra.onlinecompiler.util.BaseTestCase;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CodeEventListenerTest extends BaseTestCase {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    CodeEventListener codeEventListener;

    @Autowired
    CodeEventProcessor codeEventProcessor;

    @Autowired
    CodeExcStore codeExcStore;

    @Autowired
    ParseUtil parseUtil;

    @Test
    void test() throws InterruptedException {
        CodeEventDto dto = new CodeEventDto(
                UUID.randomUUID().toString(),
                "public class Solution {public static void main(String[] args) {System.out.println(\"Hello World\");}}",
                "jdk",
                ""
        );
        rabbitTemplate.convertAndSend("exchange", "code", parseUtil.parseToString(dto));
        codeEventListener.getLatch().await(5, TimeUnit.SECONDS);
        assertTrue(codeExcStore.checkKeyProcessed(dto.getId()));
        assertEquals("Hello World\n", codeExcStore.get(dto.getId()).output());
    }
}
