package com.tngtech.infrastructure.exception;

/**
 * Base exception class for all exceptions raised from violations to technical implementation constraints.
 *
 * May be used to store additional exception information, if available.
 */
public class SystemException extends RuntimeException {
    private static final long serialVersionUID = -7774027518004960116L;

    public SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }
}
