package com.github.amitsureshchandra.onlinecoderunner.unit_tests.controller;

import com.github.amitsureshchandra.onlinecoderunner.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecoderunner.service.mq.listener.CodeEventListener;
import com.github.amitsureshchandra.onlinecoderunner.util.BaseTestCaseControllerTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.blankString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RunnerUnitTestTestContainer extends BaseTestCaseControllerTestContainer {

    @MockBean
    CodeEventListener codeEventListener;

    @Test
    void test_code_execution() throws Exception {

        CodeReqDto dto = new CodeReqDto(
                "public class Solution {public static void main(String[] args) {System.out.println(\"Hello World\");}}",
                "jdk",
                "",
                1000
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/run")
                        .content(objectMapper.writeValueAsBytes(dto))
                        .contentType("application/json")
                        .accept("application/json")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.output", is("Hello World\n")))
                .andExpect(jsonPath("$.error", is(blankString())))
                .andExpect(jsonPath("$.exitCode", is(0)));
    }

    @Test
    void test_code_execution_with_input() throws Exception {

        CodeReqDto dto = new CodeReqDto(
                "import java.util.*;\npublic class Solution {public static void main(String[] args) {System.out.println(\"Hello \" + new Scanner(System.in).next()+\"!\");}}",
                "jdk",
                "Amit",
                1000
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/run")
                        .content(objectMapper.writeValueAsBytes(dto))
                        .contentType("application/json")
                        .accept("application/json")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.output", is("Hello Amit!\n")))
                .andExpect(jsonPath("$.error", is(blankString())))
                .andExpect(jsonPath("$.exitCode", is(0)));
    }


    @Test
    void test_compilers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/run/compilers")
                        .contentType("application/json")
                        .accept("application/json")
                )
                .andExpect(jsonPath("$.size()", is(5)))
                .andExpect(jsonPath("$.python3", is("Python 3")))
                .andExpect(jsonPath("$.node20", is("Node 20")))
                .andExpect(jsonPath("$.golang12", is("Golang")))
                .andExpect(jsonPath("$.jdk", is("Java")))
                .andExpect(jsonPath("$.gcc11", is("C/C++(gcc11)")));
    }
}
