package com.tngtech.infrastructure.exception;

/**
 * Throw this exception when you have to throw an exception in intializing code
 */
public class InitializationException extends SystemException {
    private static final long serialVersionUID = 6242772088368474500L;

    public InitializationException() {
        super();
    }

    public InitializationException(String message) {
        super(message);
    }

    public InitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitializationException(Throwable cause) {
        super(cause);
    }
}
