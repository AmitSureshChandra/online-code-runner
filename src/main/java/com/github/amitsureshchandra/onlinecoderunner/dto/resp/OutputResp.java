package com.github.amitsureshchandra.onlinecoderunner.dto.resp;

import java.io.Serializable;

public record OutputResp(String output, String error, int exitCode)  implements Serializable {

}