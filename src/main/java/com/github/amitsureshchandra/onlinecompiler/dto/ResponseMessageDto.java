package com.github.amitsureshchandra.onlinecompiler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.web.server.Http2;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessageDto {

    private String message;
    private int statusCode;

    public ResponseMessageDto(String msg){
        message = msg;
        statusCode  = HttpStatus.OK.value();
    }
}
