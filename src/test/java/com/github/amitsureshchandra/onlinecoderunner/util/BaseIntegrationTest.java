package com.github.amitsureshchandra.onlinecoderunner.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

public class BaseIntegrationTest extends BaseTestCase {
    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected RestTemplate restTemplate;

    protected Integer getServerPort() {
        return context.getEnvironment().getProperty("local.server.port", Integer.class);
    }
}
