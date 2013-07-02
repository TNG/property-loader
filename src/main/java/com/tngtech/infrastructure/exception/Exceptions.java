package com.tngtech.infrastructure.exception;

import java.lang.reflect.InvocationTargetException;

public class Exceptions {

    public static SystemException wrap(Throwable cause) {
        if (cause instanceof SystemException) {
            return (SystemException) cause;
        } else if (cause instanceof InvocationTargetException) {
            return new SystemExceptionWrapper(cause.getCause());
        } else {
            return new SystemExceptionWrapper(cause);
        }
    }

    public static Throwable unwrap(Throwable cause) {
        if (cause instanceof SystemExceptionWrapper) {
            return cause.getCause();
        }
        return cause;
    }

    private static class SystemExceptionWrapper extends SystemException {
        public SystemExceptionWrapper(Throwable cause) {
            super(cause);
        }
    }

}
