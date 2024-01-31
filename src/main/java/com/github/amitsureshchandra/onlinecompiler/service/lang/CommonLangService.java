package com.github.amitsureshchandra.onlinecompiler.service.lang;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecompiler.exception.ServerException;
import com.github.amitsureshchandra.onlinecompiler.exception.ValidationException;
import com.github.amitsureshchandra.onlinecompiler.service.docker.DockerService;
import com.github.amitsureshchandra.onlinecompiler.service.file.FileService;
import com.github.amitsureshchandra.onlinecompiler.service.shell.ShellService;
import com.github.amitsureshchandra.onlinecompiler.service.util.FileUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.exception.NotModifiedException;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class CommonLangService implements IContainerRunnerService {
    final DockerService dockerService;
    final FileService fileService;
    final ShellService shellService;

    final DockerClient dockerClient;

    @Value("${compiler-tmp-folder}")
    String compilerTmpFolder;

    @Value("${host-temp-folder}")
    String hostTempFolder;


    public CommonLangService(DockerService dockerService, FileService fileService, ShellService shellService, DockerClient dockerClient) {
        this.dockerService = dockerService;
        this.fileService = fileService;
        this.shellService = shellService;
        this.dockerClient = dockerClient;
    }

    String getCompilerTmpFolder() {
        return compilerTmpFolder + File.separator;
    }

    @Override
    public OutputResp run(CodeReqDto dto) throws IOException, InterruptedException {
        String userFolder = setUpFiles(dto);

        // creating a container
        String containerName = UUID.randomUUID().toString();

        CreateContainerResponse container =  dockerClient
            .createContainerCmd(dockerService.containerMap.get(dto.getCompiler()))
                .withHostName(containerName)
                .withHostConfig(new HostConfig().withCpuCount(1L).withCpuPercent(100L).withMemory(104857600L).withBinds(new Bind( hostTempFolder + userFolder, Volume.parse("/opt/myapp"), AccessMode.rw)))
                .exec();

        dockerClient.startContainerCmd(container.getId()).exec();

        LocalDateTime startTime = LocalDateTime.now();
        System.out.println("start time : " + startTime);

        int waitTime = 1000; // 10ms
        Thread.sleep(waitTime);


        // returning output
        LogContainerCmd logContainerCmd = dockerClient.logContainerCmd(container.getId())
                .withStdOut(true)
                .withStdErr(true)
                .withFollowStream(false);

        final StringBuilder output = new StringBuilder();
        final StringBuilder error = new StringBuilder();
        final StringBuilder fullSysOut = new StringBuilder();
        logContainerCmd.exec(new LogContainerResultCallback() {
            @Override
            public void onNext(Frame item) {
                // Process each log frame (stdout or stderr)
                fullSysOut.append(new String(item.getPayload()));
                if(item.getStreamType().equals(StreamType.STDERR)) {
                    error.append(new String(item.getPayload()));
                } else {
                    output.append(new String(item.getPayload()));
                }
            }
        }).awaitCompletion();

        postExecution(container.getId(), userFolder);

        return new OutputResp(output.toString(), error.toString(), 0);
    }

    private void postExecution(String containerId, String userFolder) {
        // stopping container
        try {
            dockerClient.stopContainerCmd(containerId).exec();
        } catch (NotModifiedException notModifiedException) {
            log.error(notModifiedException.getMessage());
        }

        // clearing docker image
        dockerClient.removeContainerCmd(containerId).exec();

        cleanUp(getCompilerTmpFolder() + userFolder);
    }

    @Override
    public void cleanUp(String folder) {
        // clearing folder
        FileUtil.deleteFolder(folder);
    }

    @Override
    public String createTempFolder() {
        String tmPfolder =  UUID.randomUUID().toString().substring(0, 6);
        String userFolder = getCompilerTmpFolder() + tmPfolder;
        if(!FileUtil.createFolder(userFolder)) {
            log.error("failed to create folder");
            throw new RuntimeException("Server Error");
        }
        return tmPfolder;
    }

    @Override
    public String setUpFiles(CodeReqDto dto) {
        String userFolder = createTempFolder();
        String filePath = getCompilerTmpFolder() +  userFolder + "/" + getFileName(dto.getCompiler());
        log.info("filePath : {}", filePath);

        if(!FileUtil.createFile(filePath, dto.getCode())) {
            log.error("failed to write to file for code");
            throw new ServerException("Server Error");
        }

        String inputFilePath = getCompilerTmpFolder() + userFolder + "/input.txt";

        if(!FileUtil.createFile(inputFilePath, dto.getInput() == null ? "" : dto.getInput())) {
            log.error("failed to write to file for input");
            throw new ServerException("Server Error");
        }
        return userFolder;
    }

    private String getFileName(String compiler) {
        switch (compiler) {
            case "jdk":
                return "Solution.java";
            case "gcc11":
                return "main.cpp";
            case "node20":
                return "app.js";
            case "golang12":
                return "main.go";
            case "python3":
                return "solution.py";
            default:
                throw new ValidationException("compiler not found");
        }
    }
}
