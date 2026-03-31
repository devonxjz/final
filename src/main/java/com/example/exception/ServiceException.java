package com.example.exception;

/**
 * Custom exception used by the Service layer to encapsulate
 * data access errors or business logic validation errors.
 */
public class ServiceException extends RuntimeException {
    
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
