package com.tristanruecker.interviewexampleproject.config.exception;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<Object> handleConflict(CustomException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getErrorMessage(), new HttpHeaders(), ex.getHttpStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream().findFirst().map(fieldError -> {
            String fieldName = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            return fieldName + " " + defaultMessage;
        }).orElse(ex.getMessage());
        return handleExceptionInternal(ex, new CustomExceptionConvertedResponseBody(status.value(), errorMessage, null), new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, new CustomExceptionConvertedResponseBody(status.value(), ex.getMessage(), null), new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, new CustomExceptionConvertedResponseBody(status.value(), ex.getMessage(), null), new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, new CustomExceptionConvertedResponseBody(status.value(), ex.getMessage(), null), new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getMessage();
        Optional<ErrorFieldList> optional = Optional.empty();
        if(ex instanceof CustomException) {
            errorMessage = ((CustomException) ex).getErrorMessage();
            optional = Optional.ofNullable(((CustomException) ex).getErrorFieldList());
        }

        return super.handleExceptionInternal(ex, new CustomExceptionConvertedResponseBody(status.value(), errorMessage, optional.orElse(null)), new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex) {
        return new ResponseEntity<>(new CustomExceptionConvertedResponseBody(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
