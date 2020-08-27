package com.mediasoft.partnersservice.common.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class ApiError {

    private final HttpStatus status;

    private final String message;

    private final List<String> errors;
}