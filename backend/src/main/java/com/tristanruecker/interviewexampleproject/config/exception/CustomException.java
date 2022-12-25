package com.tristanruecker.interviewexampleproject.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private HttpStatus httpStatus;
    private String errorMessage;
    private ErrorFieldList errorFieldList;

    public CustomException(HttpStatus httpStatusCode, String errorMessage) {
        super(errorMessage);
        this.httpStatus = httpStatusCode;
        this.errorMessage = errorMessage;
    }

    public CustomException(HttpStatus httpStatusCode, ErrorFieldList errorFieldList) {
        this.httpStatus = httpStatusCode;
        this.errorFieldList = errorFieldList;
    }

    public CustomException(HttpStatus httpStatusCode, String errorMessage, ErrorFieldList errorFieldList) {
        super(errorMessage);
        this.httpStatus = httpStatusCode;
        this.errorMessage = errorMessage;
        this.errorFieldList = errorFieldList;
    }

}

