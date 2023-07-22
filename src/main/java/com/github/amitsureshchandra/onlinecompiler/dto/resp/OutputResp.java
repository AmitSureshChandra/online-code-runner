package com.github.amitsureshchandra.onlinecompiler.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputResp {
    private String output;
    private String error;
    private int exitCode;
}
