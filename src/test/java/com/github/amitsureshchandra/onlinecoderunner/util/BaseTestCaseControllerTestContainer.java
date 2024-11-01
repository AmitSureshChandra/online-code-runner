package com.github.amitsureshchandra.onlinecoderunner.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public class BaseTestCaseControllerTestContainer extends BaseTestCaseTestContainer {
    @Autowired
    protected MockMvc mockMvc;
}
