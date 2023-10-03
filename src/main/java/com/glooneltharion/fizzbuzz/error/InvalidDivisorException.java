package com.glooneltharion.fizzbuzz.error;

public class InvalidDivisorException extends RuntimeException {
    public InvalidDivisorException(String message) {
        super(message);
    }
}
