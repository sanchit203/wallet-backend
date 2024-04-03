package com.walletbackend.exception;

public class UniqueKeyViolationException extends RuntimeException {
    public UniqueKeyViolationException(String msg) {
        super(msg);
    }
}
