package com.moira.smallhabitbackend.global.exception;

import com.moira.smallhabitbackend.global.exception.custom.HabitUserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = HabitUserException.class)
    public ResponseEntity<ErrorResponse> handleHabitUserException(HabitUserException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .errorCode(e.getErrorCode())
                .status(e.getHttpStatus())
                .time(LocalDateTime.now())
                .build();

        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }
}
