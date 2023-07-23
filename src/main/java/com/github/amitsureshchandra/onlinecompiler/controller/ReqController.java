package com.github.amitsureshchandra.onlinecompiler.controller;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecompiler.service.core.RunnerService;
import com.github.amitsureshchandra.onlinecompiler.service.docker.DockerService;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/run")
public class ReqController {

    final RunnerService runnerService;
    final DockerService dockerService;

    public ReqController(RunnerService runnerService, DockerService dockerService) {
        this.runnerService = runnerService;
        this.dockerService = dockerService;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping
    OutputResp run(@RequestBody CodeReqDto dto) throws IOException, InterruptedException {
        return runnerService.run(dto);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/compilers")
    Map<String, String> supportedCompilers() {
        return dockerService.supported();
    }
}
