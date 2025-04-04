package com.example.cryptotradingsystem.dto;

import com.example.cryptotradingsystem.enumeration.ResponseStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseResult<T>{

    /**
     * response code
     */
    private String status;

    /**
     * response message
     */
    private String message;

    /**
     * response data
     */
    private T data;

    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    public static <T> ResponseResult<T> success(T data) {
        return success(ResponseStatus.OK.getDescription(), data);
    }

    public static <T> ResponseResult<T> success(String message, T data) {
        return response(ResponseStatus.OK.getResponseCode(), message, data);
    }

    public static <T> ResponseResult<T> fail() {
        return fail(null);
    }

    public static <T> ResponseResult<T> fail(T data) {
        return fail(ResponseStatus.INTERNAL_SERVER_ERROR.getDescription(), data);
    }

    public static <T> ResponseResult<T> fail(String message, T data) {
        return response(ResponseStatus.INTERNAL_SERVER_ERROR.getResponseCode(), message, data);
    }

    public static <T> ResponseResult<T> response(ResponseStatus responseStatus, T data) {
        return response(responseStatus.getResponseCode(), responseStatus.getDescription(), data);
    }

    public static <T> ResponseResult<T> response(String status, String message, T data) {
        return ResponseResult.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }
}
