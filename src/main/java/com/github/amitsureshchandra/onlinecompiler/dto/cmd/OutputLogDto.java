package com.github.amitsureshchandra.onlinecompiler.dto.cmd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputLogDto {
    String output;      // only contains successful output
    String error;       // stores error
    String fullOutput;  // full output with errors
}
