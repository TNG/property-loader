package com.tngtech.infrastructure.exception;

import org.apache.log4j.Level;

/**
 * Base exception class for all exceptions raised from violations to business logic constraints.
 *
 * May be used to store additional exception information, if available.
 */
public abstract class DomainException extends LocalizableException {
    private static final long serialVersionUID = -7774037518004960116L;

    public DomainException() {
        super();
    }

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Object... values) {
        super(message, values);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainException(String message, Throwable cause, Object... values) {
        super(message, cause, values);
    }

    public DomainException(Throwable cause) {
        super(cause);
    }

    public Level getSeverity() {
        return Level.TRACE;
    }
}
