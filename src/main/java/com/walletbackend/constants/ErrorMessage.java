package com.walletbackend.constants;

public final class ErrorMessage {
    public static final String USERNAME_ALREADY_EXISTS = "Username already exists. Try using different username";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists. Try using different email";
    public static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password";
    public static final String TOKEN_EXPIRED = "Token expired";
    public static final String TRANSACTION_NOT_FOUND = "Transaction not found";
    public static final String INVALID_WITHDRAW_AMOUNT = "Withdraw amount can not be greater than withdrawable amount";
}
