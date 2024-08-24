package com.qquique.jag.infrastructure.database.repository.exception;

public class RepositoryException extends RuntimeException {

    public RepositoryException(String message, Exception e) {
        super(message, e);
    }
}
