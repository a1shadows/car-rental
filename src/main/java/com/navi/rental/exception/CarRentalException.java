package com.navi.rental.exception;

import lombok.Getter;

@Getter
public class CarRentalException extends Exception {
    private final ErrorCode errorCode;

    public CarRentalException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public enum ErrorCode {
        not_found,
        duplicate_entity
    }
}
