package com.github.amitsureshchandra.onlinecompiler.service.java;

import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecompiler.service.DockerService;
import com.github.amitsureshchandra.onlinecompiler.service.file.FileService;
import com.github.amitsureshchandra.onlinecompiler.service.shell.ShellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class Jdk8Service {

    Logger logger = LoggerFactory.getLogger(Jdk8Service.class);

    final DockerService dockerService;
    final FileService fileService;
    final ShellService shellService;

    public Jdk8Service(DockerService dockerService, FileService fileService, ShellService shellService) {
        this.dockerService = dockerService;
        this.fileService = fileService;
        this.shellService = shellService;
    }

    public OutputResp run(String code) throws IOException, InterruptedException {
        String userFolder = fileService.createFile(code);

        // creating a container
        String command = dockerService.getDockerCommand(userFolder);
        logger.info("command : " + command);

        // running shell service & returning output
        return shellService.run(command);
    }
}
