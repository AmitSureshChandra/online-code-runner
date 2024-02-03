package com.github.amitsureshchandra.onlinecompiler.integration_tests;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecompiler.enums.CodeExcStatus;
import com.github.amitsureshchandra.onlinecompiler.service.mq.listener.CodeEventListener;
import com.github.amitsureshchandra.onlinecompiler.util.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AsyncRunnerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    RestTemplate restTemplate;

    @Test
    void test_code_execution_with_short_polling() throws Exception {

        CodeReqDto dto = new CodeReqDto(
                "public class Solution {public static void main(String[] args) {System.out.println(\"Hello World\");}}",
                "jdk",
                ""
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // fetching compilers

        Map<String, String> compilers = restTemplate.getForEntity("http://localhost:" + getServerPort()+"/api/v1/run/compilers", Map.class, new HashMap<>()).getBody();
        assert compilers != null;
        assertEquals(5, compilers.size());

        String jsonPayload = objectMapper.writeValueAsString(new CodeReqDto(
                "public class Solution {public static void main(String[] args) {System.out.println(\"Hello World\");}}",
                "jdk",
                ""
        ));


        HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);

        String excId = restTemplate.postForEntity("http://localhost:" + getServerPort()+"/api/v1/run/async", requestEntity, String.class).getBody();
        assert excId != null;
        assertNotNull(excId);

        // short polling
        while (true) {
            String status = restTemplate.getForEntity("http://localhost:" + getServerPort()+"/api/v1/run/async/status/" + excId, String.class).getBody();
            assert status != null;
            if (status.equals(CodeExcStatus.EXECUTED.toString())) break;
            Thread.sleep(100);
        }

        OutputResp outputResp = restTemplate.getForEntity("http://localhost:" + getServerPort()+"/api/v1/run/async/output/" + excId, OutputResp.class).getBody();

        assert outputResp != null;
        assertEquals(outputResp.output(), "Hello World\n");
        assertEquals(outputResp.error(), "");
        assertEquals(outputResp.exitCode(), 0);
    }
}
