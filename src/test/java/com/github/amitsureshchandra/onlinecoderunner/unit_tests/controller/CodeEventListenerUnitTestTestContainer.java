package com.github.amitsureshchandra.onlinecoderunner.unit_tests.controller;

import com.github.amitsureshchandra.onlinecoderunner.dto.event.CodeEventDto;
import com.github.amitsureshchandra.onlinecoderunner.service.core.CodeExcStore;
import com.github.amitsureshchandra.onlinecoderunner.service.mq.listener.CodeEventListener;
import com.github.amitsureshchandra.onlinecoderunner.service.mq.processor.CodeEventProcessor;
import com.github.amitsureshchandra.onlinecoderunner.service.util.ParseUtil;
import com.github.amitsureshchandra.onlinecoderunner.util.BaseTestCaseTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CodeEventListenerUnitTestTestContainer extends BaseTestCaseTestContainer {

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
                "",
                1000
        );
        rabbitTemplate.convertAndSend("exchange", "code", parseUtil.parseToString(dto));
        codeEventListener.getLatch().await(10, TimeUnit.SECONDS);
        assertTrue(codeExcStore.checkKeyProcessed(dto.getId()));
        assertEquals("Hello World\n", codeExcStore.get(dto.getId()).output());
    }
}
