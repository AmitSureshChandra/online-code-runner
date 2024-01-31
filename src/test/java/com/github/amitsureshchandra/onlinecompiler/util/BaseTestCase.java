package com.github.amitsureshchandra.onlinecompiler.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class BaseTestCase {

    static {
//        InitTest.init();
        log.info("initialized containers & tmp directory");
    }

    @Autowired
    protected ObjectMapper objectMapper;
}
