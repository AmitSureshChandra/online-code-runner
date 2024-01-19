package com.github.amitsureshchandra.onlinecompiler.unit_tests.controller;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.dto.event.CodeEventDto;
import com.github.amitsureshchandra.onlinecompiler.service.core.CodeExcStoreService;
import com.github.amitsureshchandra.onlinecompiler.service.mq.listener.CodeEventListener;
import com.github.amitsureshchandra.onlinecompiler.service.mq.processor.CodeEventProcessor;
import com.github.amitsureshchandra.onlinecompiler.util.BaseTestCase;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CodeEventListenerTest extends BaseTestCase {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    CodeEventListener codeEventListener;

    @Autowired
    CodeEventProcessor codeEventProcessor;

    @Autowired
    CodeExcStoreService codeExcStoreService;

    @Test
    void test() throws InterruptedException {
        CodeEventDto dto = new CodeEventDto(
                UUID.randomUUID().toString(),
                "public class Solution {public static void main(String[] args) {System.out.println(\"Hello World\");}}",
                "jdk8",
                ""
        );
        rabbitTemplate.convertAndSend("exchange", "code", dto);
        codeEventListener.getLatch().await(2, TimeUnit.SECONDS);
        assertTrue(codeExcStoreService.checkKeyProcessed(dto.getId()));
        assertEquals("Hello World", codeExcStoreService.get(dto.getId()).output());
    }
}
