package com.github.amitsureshchandra.onlinecompiler.service.lang;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.service.docker.DockerService;
import com.github.amitsureshchandra.onlinecompiler.service.file.FileService;
import com.github.amitsureshchandra.onlinecompiler.service.shell.ShellService;
import com.github.amitsureshchandra.onlinecompiler.service.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class GoLangService extends CommonLangService {
    final DockerService dockerService;
    final FileService fileService;
    final ShellService shellService;
    final FileUtil fileUtil;

    public GoLangService(DockerService dockerService, FileService fileService, ShellService shellService, FileUtil fileUtil, DockerService dockerService1, FileService fileService1, ShellService shellService1, FileUtil fileUtil1) {
        super(dockerService, fileService, shellService, fileUtil);
        this.dockerService = dockerService1;
        this.fileService = fileService1;
        this.shellService = shellService1;
        this.fileUtil = fileUtil1;
    }

    @Override
    public String setUpFiles(CodeReqDto dto) {
        String userFolder = createTempFolder(dto);
        String filePath = System.getProperty("user.dir") + "/" + userFolder + "/main.go";

        if(!fileUtil.createFile(filePath, dto.getCode())) {
            log.error("failed to write to file for code");
            throw new RuntimeException("Server Error");
        }

        String inputFilePath = System.getProperty("user.dir") + "/" + userFolder + "/input.txt";

        if(!fileUtil.createFile(inputFilePath, dto.getInput())) {
            log.error("failed to write to file for code");
            throw new RuntimeException("Server Error");
        }

        return userFolder;
    }
}
