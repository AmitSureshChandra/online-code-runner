package com.github.amitsureshchandra.onlinecoderunner.dto;

import org.springframework.http.HttpStatus;

public record ResponseMessageDto(String message, int statusCode) {
    public ResponseMessageDto withMessage(String msg) {
        return new ResponseMessageDto(msg, HttpStatus.OK.value());
    }
}
