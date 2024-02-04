package com.github.amitsureshchandra.onlinecoderunner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeReqDto {
    @NotNull @NotBlank String code;
    @NotNull @NotBlank String compiler;
    String input;
}
