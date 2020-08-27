package com.mediasoft.partnersservice.common.controller;

import com.mediasoft.partnersservice.common.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController {

    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validationException(
            MethodArgumentNotValidException manve) {

        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        var message = "Validation error..";
        var errors = manve.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        logger.error(message, manve);
        return new ResponseEntity<>(
                ApiError.builder()
                        .status(status)
                        .message(message)
                        .errors(errors)
                        .build(),
                status);
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> httpMessageNotReadableException(
            HttpMessageNotReadableException hmnre)
            throws HttpMessageNotReadableException {
        var status = HttpStatus.BAD_REQUEST;
        var message = "Request parsing error..";
        logger.error(message, hmnre);
        return new ResponseEntity<>(
                ApiError.builder()
                        .status(status)
                        .message(message)
                        .errors(Arrays.asList(hmnre.getMessage()))
                        .build(),
                status);
    }

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> resourceNotFoundException(
            ResourceNotFoundException rnfe) {
        var status = HttpStatus.NOT_FOUND;
        var message = "Resource not found..";
        logger.error(message, rnfe);
        return new ResponseEntity<>(
                ApiError.builder()
                        .status(status)
                        .message(message)
                        .errors(Arrays.asList(rnfe.getMessage()))
                        .build(),
                status);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> otherException(Exception e)
            throws Exception {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var message = "Something went wrong..";
        logger.error(message, e);
        return new ResponseEntity<>(
                ApiError.builder()
                        .status(status)
                        .message(message)
                        .errors(Arrays.asList(e.getMessage()))
                        .build(),
                status);
    }
}