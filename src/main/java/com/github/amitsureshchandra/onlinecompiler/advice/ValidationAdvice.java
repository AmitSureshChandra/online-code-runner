package com.github.amitsureshchandra.onlinecompiler.advice;

import com.github.amitsureshchandra.onlinecompiler.dto.ResponseMessageDto;
import com.github.amitsureshchandra.onlinecompiler.exception.ServerException;
import com.github.amitsureshchandra.onlinecompiler.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationAdvice {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessageDto validationException(ValidationException ex){
        return new ResponseMessageDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(ServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessageDto serverException(ServerException ex){
        return new ResponseMessageDto(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}
