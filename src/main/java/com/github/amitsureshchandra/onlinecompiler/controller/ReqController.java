package com.github.amitsureshchandra.onlinecompiler.controller;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecompiler.service.core.RunnerService;
import com.github.amitsureshchandra.onlinecompiler.service.docker.DockerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    OutputResp run(@RequestBody @Valid CodeReqDto dto) {
        return runnerService.run(dto);
    }

    @GetMapping("/compilers")
    Map<String, String> supportedCompilers() {
        return dockerService.supported();
    }
}
