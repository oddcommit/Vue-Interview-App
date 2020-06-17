package com.tristanruecker.interviewexampleproject.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private HttpStatus httpStatus;
    private String errorMessage;

    public CustomException(HttpStatus httpStatusCode, String errorMessage) {
        super(errorMessage);
        this.httpStatus = httpStatusCode;
        this.errorMessage = errorMessage;
    }

}

