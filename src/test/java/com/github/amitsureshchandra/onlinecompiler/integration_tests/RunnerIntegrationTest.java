package com.github.amitsureshchandra.onlinecompiler.integration_tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecompiler.service.mq.listener.CodeEventListener;
import com.github.amitsureshchandra.onlinecompiler.util.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RunnerIntegrationTest extends BaseIntegrationTest {

    @MockBean
    CodeEventListener codeEventListener;

    @Test
    void run_test() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // fetching compilers

        Map<String, String> compilers = restTemplate.getForEntity("http://localhost:" + getServerPort()+"/api/v1/run/compilers", Map.class, new HashMap<>()).getBody();
        assert compilers != null;
        assertEquals(5, compilers.size());

        // without input

        String jsonPayload = objectMapper.writeValueAsString(new CodeReqDto(
                "public class Solution {public static void main(String[] args) {System.out.println(\"Hello World\");}}",
                "jdk",
                ""
        ));


        HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);

        OutputResp outputResp = restTemplate.postForEntity("http://localhost:" + getServerPort()+"/api/v1/run", requestEntity, OutputResp.class).getBody();
        assert outputResp != null;
        assertEquals(outputResp.output(), "Hello World\n");
        assertEquals(outputResp.error(), "");
        assertEquals(outputResp.exitCode(), 0);

        // with input

        jsonPayload = objectMapper.writeValueAsString(new CodeReqDto(
                "import java.util.*;\npublic class Solution {public static void main(String[] args) {System.out.println(\"Hello \" + new Scanner(System.in).next()+\"!\");}}",
                "jdk",
                "Amit"
        ));

        requestEntity = new HttpEntity<>(jsonPayload, headers);

        outputResp = restTemplate.postForEntity("http://localhost:" + getServerPort()+"/api/v1/run", requestEntity, OutputResp.class).getBody();
        assert outputResp != null;
        assertEquals(outputResp.output(), "Hello Amit!\n");
        assertEquals(outputResp.error(), "");
        assertEquals(outputResp.exitCode(), 0);

        // with errors

        jsonPayload = objectMapper.writeValueAsString(new CodeReqDto(
                "import java.util.*;\npublic class Solution {public static void main(String[] args) {System.out.println(\"Hello \" + new Scanner(System.in).next()+\"!\")}}",
                "jdk",
                "Amit"
        ));

        requestEntity = new HttpEntity<>(jsonPayload, headers);

        outputResp = restTemplate.postForEntity("http://localhost:" + getServerPort()+"/api/v1/run", requestEntity, OutputResp.class).getBody();
        System.out.println(outputResp);
        assert outputResp != null;
        assertEquals(outputResp.output(), "");
        assertEquals(outputResp.error(), "Solution.java:2: error: ';' expected\n" +
                "public class Solution {public static void main(String[] args) {System.out.println(\"Hello \" + new Scanner(System.in).next()+\"!\")}}\n" +
                "                                                                                                                               ^\n" +
                "1 error\n");
        assertEquals(outputResp.exitCode(), 0);
    }
}
