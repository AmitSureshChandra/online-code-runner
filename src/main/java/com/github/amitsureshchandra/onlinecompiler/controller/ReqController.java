package com.github.amitsureshchandra.onlinecompiler.controller;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.service.RunnerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@RequestMapping("/api/v1/run")
public class ReqController {

    final RunnerService runnerService;

    public ReqController(RunnerService runnerService) {
        this.runnerService = runnerService;
    }

    @PostMapping
    String run(@RequestBody CodeReqDto dto) throws IOException, InterruptedException {
        return runnerService.run(dto);
    }
}
