package com.github.amitsureshchandra.onlinecompiler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CodeReqDto(@NotNull @NotBlank String code,@NotNull @NotBlank String compiler, String input) {

}
