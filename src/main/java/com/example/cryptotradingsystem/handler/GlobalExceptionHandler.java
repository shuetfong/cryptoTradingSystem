package com.example.cryptotradingsystem.handler;


import com.example.cryptotradingsystem.dto.ResponseResult;
import com.example.cryptotradingsystem.enumeration.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult<Object> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        logError(e);
        return ResponseResult.response(ResponseStatus.METHOD_NOT_ALLOWED, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        logError(e);
        List<String> errors = e.getFieldErrors().stream().map(fieldError -> String.format("Please provide a valid %s.", fieldError.getField())).collect(Collectors.toList());
        return ResponseResult.response(ResponseStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseResult<Object> methodArgumentNotValidExceptionHandler(NoResourceFoundException e) {
        logError(e);
        return ResponseResult.response(ResponseStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseResult<Object> runtimeExceptionHandler(RuntimeException e) {
        logError(e);
        return ResponseResult.response(ResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult<?> generalExceptionHandler(Exception e) {
        logError(e);
        return ResponseResult.fail("An unexpected error occurred. Please try again later.");
    }

    private void logError(Exception e) {
        log.error("Exception: " + e.getMessage());
        e.printStackTrace();
    }
}
