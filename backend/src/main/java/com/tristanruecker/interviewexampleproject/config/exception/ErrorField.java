package com.tristanruecker.interviewexampleproject.config.exception;

import lombok.Data;

@Data
public class ErrorField {
    private String field;
    private String errorMessage;

    public ErrorField(String field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
    }
}

