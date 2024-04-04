package com.walletbackend.exception;

public class InvalidInputDataException extends RuntimeException {
    public InvalidInputDataException(String msg) {
        super(msg);
    }
}
