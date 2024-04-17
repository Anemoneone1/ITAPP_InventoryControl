package com.itapp.inventorycontrol.exception;

import lombok.Getter;

@Getter
public class ICException extends RuntimeException {
    private final ICErrorType errorType;


    public ICException(ICErrorType errorType) {
        super(errorType.name());
        this.errorType = errorType;
    }
}
