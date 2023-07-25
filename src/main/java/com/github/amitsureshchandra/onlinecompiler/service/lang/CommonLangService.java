package com.github.amitsureshchandra.onlinecompiler.service.lang;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecompiler.exception.ServerException;
import com.github.amitsureshchandra.onlinecompiler.service.docker.DockerService;
import com.github.amitsureshchandra.onlinecompiler.service.file.FileService;
import com.github.amitsureshchandra.onlinecompiler.service.shell.ShellService;
import com.github.amitsureshchandra.onlinecompiler.service.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public abstract class CommonLangService implements IContainerRunnerService {

    final DockerService dockerService;
    final FileService fileService;
    final ShellService shellService;
    final FileUtil fileUtil;

    public CommonLangService(DockerService dockerService, FileService fileService, ShellService shellService, FileUtil fileUtil) {
        this.dockerService = dockerService;
        this.fileService = fileService;
        this.shellService = shellService;
        this.fileUtil = fileUtil;
    }

    @Override
    public OutputResp run(CodeReqDto dto) throws IOException, InterruptedException {
        String userFolder = setUpFiles(dto);

        // creating a container
        String containerName = UUID.randomUUID().toString();
        String command = dockerService.getDockerCommand(userFolder, dto.getCompiler(), containerName);
        log.info("command : " + command);

        // running shell service & returning output
        OutputResp outputResp = shellService.run(command);

        // clearing docker image
        shellService.run("docker rm " + containerName);

        cleanUp(userFolder);

        return outputResp;
    }

    @Override
    public void cleanUp(String folder) {
        // clearing folder
        fileUtil.deleteFolder(folder);
    }

    @Override
    public String createTempFolder(CodeReqDto dto) {
        String userFolder = "temp/" + UUID.randomUUID().toString().substring(0, 6);
        if(!fileUtil.createFolder(userFolder)) {
            log.error("failed to create folder");
            throw new ServerException("Server Error");
        }
        return userFolder;
    }
}
