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
public class JavaLangService extends CommonLangService {
    final DockerService dockerService;
    final FileService fileService;
    final ShellService shellService;
    final FileUtil fileUtil;

    public JavaLangService(DockerService dockerService, FileService fileService, ShellService shellService, FileUtil fileUtil) {
        super(dockerService, fileService, shellService, fileUtil);
        this.dockerService = dockerService;
        this.fileService = fileService;
        this.shellService = shellService;
        this.fileUtil = fileUtil;
    }

    @Override
    public String setUpFiles(CodeReqDto dto) {
        String userFolder = createTempFolder(dto);
        String filePath = System.getProperty("user.dir") + "/" + userFolder + "/Solution.java";
        String inputFilePath = System.getProperty("user.dir") + "/" + userFolder + "/input.txt";

        if(!fileUtil.createFile(filePath, dto.getCode())) {
            log.error("failed to write to file for code");
            throw new RuntimeException("Server Error");
        }

        if(!fileUtil.createFile(inputFilePath, dto.getInput() == null ? "" : dto.getInput())) {
            log.error("failed to write to file for input");
            throw new RuntimeException("Server Error");
        }
        return userFolder;
    }
}
