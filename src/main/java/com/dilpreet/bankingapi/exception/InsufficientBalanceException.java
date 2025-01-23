package com.dilpreet.bankingapi.exception;

public class InsufficientBalanceException  extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
