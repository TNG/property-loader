package com.tngtech.infrastructure.exception;

/**
 * Throw this exception when a precondition in a method call does not hold.
 * @see com.tngtech.infrastructure.exception.Reject
 */
public class PreconditionException extends SystemException {
    private static final long serialVersionUID = -6204840303149650090L;

    public PreconditionException() {
        super();
    }

    public PreconditionException(String message) {
        super(message);
    }

    public PreconditionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PreconditionException(Throwable cause) {
        super(cause);
    }
}
