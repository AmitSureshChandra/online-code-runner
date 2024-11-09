package com.github.amitsureshchandra.onlinecoderunner.service.core;

import com.github.amitsureshchandra.onlinecoderunner.config.MQConfig;
import com.github.amitsureshchandra.onlinecoderunner.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecoderunner.dto.cmd.OutputLogDto;
import com.github.amitsureshchandra.onlinecoderunner.dto.event.CodeEventDto;
import com.github.amitsureshchandra.onlinecoderunner.dto.resp.OutputResp;
import com.github.amitsureshchandra.onlinecoderunner.enums.CodeExcStatus;
import com.github.amitsureshchandra.onlinecoderunner.exception.ServerException;
import com.github.amitsureshchandra.onlinecoderunner.exception.ValidationException;
import com.github.amitsureshchandra.onlinecoderunner.service.docker.IDockerService;
import com.github.amitsureshchandra.onlinecoderunner.service.util.FileUtil;
import com.github.amitsureshchandra.onlinecoderunner.service.util.ParseUtil;
import com.github.amitsureshchandra.onlinecoderunner.service.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

@Slf4j
@Service
public class RunnerServiceImpl implements IRunnerService {
    final IDockerService IDockerService;

    final RabbitTemplate rabbitTemplate ;

    final CodeExcStore codeExcStore;

    final ParseUtil parseUtil;

    Queue<String[]> removeContainerQueue = new LinkedList<>();

    @Value("${tmp-folder}")
    String tmpFolder;

    final ModelMapper modelMapper;

    public RunnerServiceImpl(IDockerService IDockerService, RabbitTemplate rabbitTemplate , CodeExcStore codeExcStore, ParseUtil parseUtil, ModelMapper modelMapper) {
        this.IDockerService = IDockerService;
        this.rabbitTemplate  = rabbitTemplate ;
        this.codeExcStore = codeExcStore;
        this.parseUtil = parseUtil;
        this.modelMapper = modelMapper;
    }

    String getTmpFolder() {
        return tmpFolder + File.separator;
    }

    String getFilePath(String fileName, String userFolder) {
        return tmpFolder + File.separator +  userFolder + File.separator + fileName;
    }

    public String createTempFolder() {
        String tmpfolder =  UUID.randomUUID().toString().substring(0, 6);
        String userFolder = getTmpFolder() + tmpfolder;
        if(!FileUtil.createFolder(userFolder)) {
            log.error("failed to create folder");
            throw new RuntimeException("Server Error");
        }
        return tmpfolder;
    }

    @Override
    public String storeCode(CodeReqDto codeReqDto) {
        String userFolder = createTempFolder();

        if(!FileUtil.createFile(getFilePath(getFileName(codeReqDto.getCompiler()), userFolder), codeReqDto.getCode())) {
            log.error("failed to write to file for code");
            throw new ServerException("Server Error");
        }

        if(!FileUtil.createFile(getFilePath("input.txt", userFolder), codeReqDto.getInput() == null ? "" : codeReqDto.getInput())) {
            log.error("failed to write to file for input");
            throw new ServerException("Server Error");
        }
        return userFolder;
    }

    @Override
    public OutputResp runCode(CodeReqDto codeReqDto) {

        int waitTime = codeReqDto.getTimeout();

        if(waitTime > 1000 * 10) throw new ValidationException("timeout can't be more than 10s");

        String userFolder = storeCode(codeReqDto);

        String containerId = IDockerService.createContainer(codeReqDto.getCompiler(), userFolder);
        IDockerService.startContainer(containerId);

        LocalDateTime startTime = LocalDateTime.now();
        log.info("start time : " + startTime);

        log.info("wait time : " + waitTime);

        TimeUtil.sleep(waitTime);

        log.info("Waited till " + LocalDateTime.now());

        IDockerService.stopContainer(containerId);

        // returning output
        OutputLogDto outputLogDto = IDockerService.getContainerLogs(containerId);

        log.info("log read at " + LocalDateTime.now());

        postCleanUp(tmpFolder, containerId);
        log.info(containerId + " is removed");
        return new OutputResp(outputLogDto.getOutput(), outputLogDto.getError(), 0);
    }

    @Override
    public String runCodeAsync(CodeReqDto codeReqDto) {
        UUID excId = UUID.randomUUID();
        CodeEventDto codeEventDto = modelMapper.map(codeReqDto, CodeEventDto.class);
        codeEventDto.setId(excId.toString());
        rabbitTemplate.convertAndSend(MQConfig.exchangeName, "code", parseUtil.parseToString(codeEventDto));
        return excId.toString();
    }

    @Override
    public String getCodeExcStatus(String excId) {
        return codeExcStore.checkKeyProcessed(excId) ? CodeExcStatus.EXECUTED.toString() : CodeExcStatus.PENDING.toString();
    }

    @Override
    public OutputResp getOutput(String excId) {
        return codeExcStore.get(excId);
    }

    @Override
    public void postCleanUp(String userFolder, String  containerId) {
        // clearing docker container
        IDockerService.removeContainer(containerId);

        // clear temp directory
        FileUtil.deleteFolder(getTmpFolder() + userFolder);
    }
}
