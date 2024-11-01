package com.github.amitsureshchandra.onlinecoderunner.service.docker;

import com.github.amitsureshchandra.onlinecoderunner.dto.cmd.OutputLogDto;
import com.github.amitsureshchandra.onlinecoderunner.exception.ServerException;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Slf4j
@Service
public class DockerServiceImpl implements IDockerService {
    final DockerClient dockerClient;

    @Value("${tmp-folder}")
    String tmpFolder;

    public DockerServiceImpl(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    @PostConstruct
    void init() {
        containerMap.put("jdk", "online-compiler-jdk");
        containerMapInfo.put("jdk", "Java");

        containerMap.put("golang12", "online-compiler-golang12");
        containerMapInfo.put("golang12", "Golang");

        containerMap.put("python3", "online-compiler-python3");
        containerMapInfo.put("python3", "Python 3");

        containerMap.put("node20", "online-compiler-node20");
        containerMapInfo.put("node20", "Node 20");

        containerMap.put("gcc11", "online-compiler-gcc11");
        containerMapInfo.put("gcc11", "C/C++(gcc11)");
    }

    @Override
    public String createContainer(String compiler, String userFolder) {
        CreateContainerResponse container =  dockerClient
                .createContainerCmd(IDockerService.containerMap.get(compiler))
                .withHostName(UUID.randomUUID().toString())
                .withHostConfig(new HostConfig().withCpuCount(1L).withCpuPercent(100L).withMemory(104857600L).withBinds(new Bind( tmpFolder + File.separator + userFolder, Volume.parse("/opt/myapp"), AccessMode.rw)))
                .exec();

        return container.getId();
    }

    @Override
    public void startContainer(String containerId) {
        dockerClient.startContainerCmd(containerId).exec();
    }

    @Override
    public void stopContainer(String containerId) {
        dockerClient.stopContainerCmd(containerId).exec();
    }

    @Override
    public OutputLogDto getContainerLogs(String containerId) {
        LogContainerCmd logContainerCmd = dockerClient.logContainerCmd(containerId)
                .withStdOut(true)
                .withStdErr(true)
                .withFollowStream(false);

        final StringBuilder output = new StringBuilder();
        final StringBuilder error = new StringBuilder();
        final StringBuilder fullSysOut = new StringBuilder();

        try {
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
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new ServerException("some error occurred");
        }

        return new OutputLogDto(output.toString(), error.toString(), fullSysOut.toString());
    }

    @Override
    public void removeContainer(String containerId) {
        dockerClient.removeContainerCmd(containerId).exec();
    }
}
