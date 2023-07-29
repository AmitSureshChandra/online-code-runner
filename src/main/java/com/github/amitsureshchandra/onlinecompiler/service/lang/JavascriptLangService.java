package com.github.amitsureshchandra.onlinecompiler.service.lang;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.exception.ServerException;
import com.github.amitsureshchandra.onlinecompiler.service.docker.DockerService;
import com.github.amitsureshchandra.onlinecompiler.service.file.FileService;
import com.github.amitsureshchandra.onlinecompiler.service.shell.ShellService;
import com.github.amitsureshchandra.onlinecompiler.service.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JavascriptLangService extends CommonLangService{

    final DockerService dockerService;
    final FileService fileService;
    final ShellService shellService;
    final FileUtil fileUtil;

    public JavascriptLangService(DockerService dockerService, FileService fileService, ShellService shellService, FileUtil fileUtil) {
        super(dockerService, fileService, shellService, fileUtil);
        this.dockerService = dockerService;
        this.fileService = fileService;
        this.shellService = shellService;
        this.fileUtil = fileUtil;
    }

    @Override
    public String setUpFiles(CodeReqDto dto) {
        String userFolder = createTempFolder(dto);
        String filePath = System.getProperty("user.dir") + "/" + userFolder + "/app.js";

        if(!fileUtil.createFile(filePath, dto.getCode())) {
            log.error("failed to write to file for code");
            throw new ServerException("Server Error");
        }

        String inputFilePath = System.getProperty("user.dir") + "/" + userFolder + "/input.txt";

        if(!fileUtil.createFile(inputFilePath, dto.getInput() == null ? "" : dto.getInput())) {
            log.error("failed to write to file for input");
            throw new ServerException("Server Error");
        }
        return userFolder;
    }
}
