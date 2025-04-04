package com.example.cryptotradingsystem.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    /**
     * Successful
     */
    OK("200", "OK"),

    /**
     * Client Error
     */
    BAD_REQUEST("400", "Bad Request"),
    UNAUTHORIZED("401", "Unauthorized"),
    FORBIDDEN("403", "Forbidden"),
    NOT_FOUND("404", "Not Found"),
    METHOD_NOT_ALLOWED("405", "Method Not Allowed"),

    /**
     * Internal Server Error
     */
    INTERNAL_SERVER_ERROR("500", "Internal Server Error");

    private final String responseCode;
    private final String description;
}
