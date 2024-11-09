package com.github.amitsureshchandra.onlinecoderunner.controller;

import com.github.amitsureshchandra.onlinecoderunner.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecoderunner.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecoderunner.service.core.IRunnerService;
import com.github.amitsureshchandra.onlinecoderunner.service.docker.IDockerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/run")
public class RunController {
    final IRunnerService IRunnerService;
    final IDockerService IDockerService;

    public RunController(IRunnerService IRunnerService, IDockerService IDockerService) {
        this.IRunnerService = IRunnerService;
        this.IDockerService = IDockerService;
    }

    @PostMapping
    OutputResp run(@RequestBody @Valid CodeReqDto dto) {
        return IRunnerService.runCode(dto);
    }

    @PostMapping("/async")
    String runAsync(@RequestBody @Valid CodeReqDto dto) {
        return IRunnerService.runCodeAsync(dto);
    }

    @GetMapping("/async/status/{excId}")
    String runAsync(@PathVariable String excId) {
        return IRunnerService.getCodeExcStatus(excId);
    }

    @GetMapping("/async/output/{excId}")
    OutputResp runAsyncOutput(@PathVariable String excId) {
        return IRunnerService.getOutput(excId);
    }

    @GetMapping("/compilers")
    Map<String, String> supportedCompilers() {
        return IDockerService.getSupportedLanguages();
    }
}
