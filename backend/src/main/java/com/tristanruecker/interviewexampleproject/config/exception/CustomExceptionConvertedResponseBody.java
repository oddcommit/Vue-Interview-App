package com.tristanruecker.interviewexampleproject.config.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class CustomExceptionConvertedResponseBody {
    private int httpStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErrorFieldList errorFields;

    public CustomExceptionConvertedResponseBody(int httpStatus, String errorMessage, ErrorFieldList errorFields) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
        this.errorFields = errorFields;
    }
}
