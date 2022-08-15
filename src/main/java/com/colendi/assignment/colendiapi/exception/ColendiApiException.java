package com.colendi.assignment.colendiapi.exception;

import lombok.Getter;

@Getter
public class ColendiApiException extends RuntimeException {

    private final ErrorCodeEnum errorCode;

    public ColendiApiException(ErrorCodeEnum errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
