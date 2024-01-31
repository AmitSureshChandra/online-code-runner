package com.github.amitsureshchandra.onlinecompiler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.amitsureshchandra.onlinecompiler.dto.event.CodeEventDto;
import com.github.amitsureshchandra.onlinecompiler.service.mq.listener.CodeEventListener;
import com.github.amitsureshchandra.onlinecompiler.util.BaseTestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class OnlineCompilerApplicationTests extends BaseTestCase {

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CodeEventListener codeEventListener;

    @Test
    void contextLoads() throws JsonProcessingException {
        String str = "{\"id\":\"950440b3-1be8-4590-a602-80f7d07474ba\",\"code\":\"import java.util.Scanner;\\n\\nclass Solution {\\n    public static void main(String[] args) {\\n        Problem sol = new Problem();\\n        Scanner sc = new Scanner(System.in);\\n        int testCases = sc.nextInt();\\n        Answer ans = new Answer();\\n\\n        for (int i = 0; i < testCases; i++) {\\n            int n = sc.nextInt();\\n            long submitted = sol.factorial(n);\\n            long expectedAns = ans.factorial(n);\\n            if(submitted == expectedAns) {\\n                System.out.println(\\\"Pass:\\\"+ submitted + \\\"~\\\"+ expectedAns);\\n            }else {\\n                System.out.println(\\\"Fail:\\\"+ submitted + \\\"~\\\"+ expectedAns);\\n            }\\n        }\\n    }\\n}\\n\\nclass Answer {\\n    public long factorial(int n) {\\n        if(n == 0 || n == 1) return 1;\\n        long fact = 1;\\n        for(int i=2; i<= n; i++) fact *= i;\\n        return fact;\\n    }\\n}\\n\\n// user code will go below\\n// in append mode\\n\\nclass Problem  {\\n    long factorial(int n) {\\n        int fact = 1;\\n        for(int i=1; i<= n; i++) fact *= i;\\n        return fact;\\n    }\\n} \",\"compiler\":\"jdk\",\"input\":\"4\\n0\\n1\\n6\\n5\\n\"}";

        System.out.println(objectMapper.readValue(str, CodeEventDto.class));


    }

}
