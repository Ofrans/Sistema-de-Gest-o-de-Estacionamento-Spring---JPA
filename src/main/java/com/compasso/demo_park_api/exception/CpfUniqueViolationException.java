package com.compasso.demo_park_api.exception;

public class CpfUniqueViolationException extends RuntimeException {
    public CpfUniqueViolationException(String s) {
        super(s);
    }
}
