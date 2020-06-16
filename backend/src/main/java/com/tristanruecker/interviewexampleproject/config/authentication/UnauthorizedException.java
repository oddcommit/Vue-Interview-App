package com.tristanruecker.interviewexampleproject.config.authentication;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@ToString
class UnauthorizedException {

    private HttpStatus httpStatus;
    private Instant currentTime;

    @Getter
    private String errorMessage;

    UnauthorizedException(HttpStatus httpStatusCode, String errorMessage) {
        this.httpStatus = httpStatusCode;
        this.errorMessage = errorMessage;
        this.currentTime = Instant.now();
    }

    public int getHttpStatus() {
        return httpStatus.value();
    }
}
