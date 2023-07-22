package com.github.amitsureshchandra.onlinecompiler.service;

import com.github.amitsureshchandra.onlinecompiler.dto.CodeReqDto;
import com.github.amitsureshchandra.onlinecompiler.dto.resp.OutputResp;

import java.io.IOException;

public interface IContainerRunnerService {
    OutputResp run(CodeReqDto dto) throws IOException, InterruptedException;
}
