package com.moira.smallhabitbackend.global.exception.custom;

import com.moira.smallhabitbackend.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HabitBookException extends RuntimeException {
    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;

    public HabitBookException(ErrorCode errorCode, HttpStatus httpStatus) {
        super(errorCode.getMessage());

        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
