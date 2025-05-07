package com.ssg.order_management.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}


