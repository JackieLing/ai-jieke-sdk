package com.jieke.coze.exception;

public class CozeException extends RuntimeException {
    public CozeException(String message) {
        super(message);
    }

    public CozeException(String message, Throwable cause) {
        super(message, cause);
    }
}