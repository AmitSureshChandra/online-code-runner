package com.github.amitsureshchandra.onlinecoderunner.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class BaseTestCaseTestContainer extends BaseTestContainer {

    static {
//        InitTest.init();
        log.info("initialized containers & tmp directory");
    }

    @Autowired
    protected ObjectMapper objectMapper;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
          registry.add("spring.rabbitmq.username", rabbit::getAdminUsername);
          registry.add("spring.rabbitmq.password", rabbit::getAdminPassword);
          registry.add("spring.rabbitmq.host", rabbit::getHost);
          registry.add("spring.rabbitmq.virtual-host",() -> "/");
          registry.add("spring.rabbitmq.port", rabbit::getAmqpPort);

          registry.add("spring.data.redis.host", redis::getHost);
          registry.add("spring.data.redis.port", redis::getRedisPort);
    }
}
