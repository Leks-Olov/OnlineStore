package com.lex.controller;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class BadRequestException  extends RuntimeException{

    private final Map<String, String> errors;

    public BadRequestException(Map<String, String> errors) {
        this.errors = errors;
    }

    public BadRequestException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public BadRequestException(String message, Throwable cause, Map<String, String> errors) {
        super(message, cause);
        this.errors = errors;
    }

    public BadRequestException(Throwable cause, Map<String, String> errors) {
        super(cause);
        this.errors = errors;
    }

    public BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map<String, String> errors) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errors = errors;
    }

}
