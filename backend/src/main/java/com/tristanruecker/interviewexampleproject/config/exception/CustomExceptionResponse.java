package com.tristanruecker.interviewexampleproject.config.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomExceptionResponse {

    private int httpStatus;
    private String errorMessage;

    public CustomExceptionResponse(int httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;

    }
}