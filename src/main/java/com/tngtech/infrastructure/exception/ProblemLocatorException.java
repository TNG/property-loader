package com.tngtech.infrastructure.exception;

/**
 * Exception that is helpful when you want to add a "pseudo" exception to a log.
 * Normally used like this:
 * <code>
 *    LOG.error("someMethod() - description of unexpected situation", new ProblemLocatorException());
 * </code>
 */
public class ProblemLocatorException extends SystemException {

    private static final long serialVersionUID = -2497370984773816689L;

    public ProblemLocatorException() {
        super("here");
    }
}
