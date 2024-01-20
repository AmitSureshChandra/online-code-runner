package com.github.amitsureshchandra.onlinecompiler.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeEventDto {
    private String id;
    private String code;
    private String compiler;
    private String input;
}
