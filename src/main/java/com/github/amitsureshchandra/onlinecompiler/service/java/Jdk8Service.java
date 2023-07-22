package com.github.amitsureshchandra.onlinecompiler.service.java;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecompiler.service.docker.DockerService;
import com.github.amitsureshchandra.onlinecompiler.service.IContainerRunnerService;
import com.github.amitsureshchandra.onlinecompiler.service.file.FileService;
import com.github.amitsureshchandra.onlinecompiler.service.shell.ShellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;

@Service
public class Jdk8Service implements IContainerRunnerService {

    Logger logger = LoggerFactory.getLogger(Jdk8Service.class);

    final DockerService dockerService;
    final FileService fileService;
    final ShellService shellService;

    public Jdk8Service(DockerService dockerService, FileService fileService, ShellService shellService) {
        this.dockerService = dockerService;
        this.fileService = fileService;
        this.shellService = shellService;
    }

    @Override
    public OutputResp run(CodeReqDto dto) throws IOException, InterruptedException {
        String userFolder = fileService.createFile(dto.getCode(), dto.getInput());

        // creating a container
        String containerName = UUID.randomUUID().toString();
        String command = dockerService.getDockerCommand(userFolder, dto.getCompiler(), containerName);
        logger.info("command : " + command);

        // running shell service & returning output
        OutputResp outputResp = shellService.run(command);

        // clearing docker image
        shellService.run("docker rm " + containerName);

        // clearing folder
        fileService.deleteFolder(userFolder);

        return outputResp;
    }
}
