package com.qquique.jag.application.api.exception;

public class ControllerException extends RuntimeException {
    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}
