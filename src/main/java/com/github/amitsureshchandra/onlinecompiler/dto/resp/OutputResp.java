package com.github.amitsureshchandra.onlinecompiler.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutputResp {
    private String output;
    private String error;
    private int exitCode;
}
