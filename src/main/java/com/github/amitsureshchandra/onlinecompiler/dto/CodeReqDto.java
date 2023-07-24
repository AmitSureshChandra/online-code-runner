package com.github.amitsureshchandra.onlinecompiler.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CodeReqDto {

    @NotNull
    @NotBlank
    private String code;

    @NotNull
    @NotBlank
    private String compiler;

    private String input;
}
