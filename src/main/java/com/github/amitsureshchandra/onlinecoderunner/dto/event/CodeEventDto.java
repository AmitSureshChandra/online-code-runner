package com.github.amitsureshchandra.onlinecoderunner.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeEventDto {
    private String id;
    private String code;
    private String compiler;
    private String input;
    private int timeout;
}
