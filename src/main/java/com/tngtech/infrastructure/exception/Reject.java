package com.tngtech.infrastructure.exception;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Collection;

/**
 * Utility class to make it easier and better readable to check parameters at the beginning of methods
 * Reject.always(...) is pointless because it cannot be a "pre condition" to uncontionally fail.
 * Throw appropriate standard Java exceptions, like
 * {@link UnsupportedOperationException}, {@link IllegalStateException}, {@link IllegalArgumentException}, etc. if you need this.
 */
public abstract class Reject {

    public static void ifTrue(boolean condition) {
        ifTrue(null, condition);
    }

    public static void ifTrue(String description, boolean condition) {
        if (condition) {
            if (description == null) {
                description = "ifTrue";
            }
            description += ": expected false, was true";
            throw new PreconditionException(description);
        }
    }

    public static void ifFalse(boolean condition) {
        ifFalse(null, condition);
    }

    public static void ifFalse(String description, boolean condition) {
        if (!condition) {
            if (description == null) {
                description = "ifFalse";
            }
            description += ": expected true, was false";
            throw new PreconditionException(description);
        }
    }

    public static void ifNull(Object objectToTest) {
        ifNull(null, objectToTest);
    }

    public static void ifAnyNull(Object... objectsToTest) {
        ifNull(null, objectsToTest);
        for (Object obj : objectsToTest) {
            ifNull(null, obj);
        }
    }

    public static void ifNull(String description, Object objectToTest) {
        if (objectToTest == null) {
            if (description == null) {
                description = "ifNull";
            }
            description += ": expected to be not null, but is null";
            throw new PreconditionException(description);
        }
    }

    public static void ifNotNull(Object objectToTest) {
        ifNotNull(null, objectToTest);
    }

    public static void ifNotNull(String description, Object objectToTest) {
        if (objectToTest != null) {
            if (description == null) { description = "ifNull"; }

            description += ": expected to be null, but is non-null";
            throw new PreconditionException(description);
        }
    }

    public static void ifBlank(String stringToTest) {
        ifBlank(null, stringToTest);
    }

    public static void ifBlank(String description, String stringToTest) {
        if (description == null) {
            description = "ifBlank";
        }
        ifTrue(description, isBlank(stringToTest));
    }

    public static void ifEquals(String description, Object unexpected, Object actual) {
        if (unexpected.equals(actual)) {
            if (description == null) {
                description = "ifEquals";
            }
            description += ": expected anything but <" +  unexpected + ">, but was <" + actual + ">";
            throw new PreconditionException(description);
        }
    }

    public static void ifSame(Object unexpected, Object actual) {
        ifSame(null, unexpected, actual);
    }

    public static void ifSame(String description, Object unexpected, Object actual) {
        if (unexpected == actual) {
            if (description == null) {
                description = "ifSame";
            }
            description += ": expected anything but <" + unexpected + ">, but was <" + actual + ">";
            throw new PreconditionException(description);
        }
    }

    public static void ifNotEquals(String description, Object expected, Object actual) {
        if (!expected.equals(actual)) {
            if (description == null) {
                description = "ifNotEquals";
            }
            description += ": expected <" +  expected + "> but was <" + actual + ">";
            throw new PreconditionException(description);
        }
    }

    public static void ifEmpty(Object[] array) {
        ifEmpty(null, array);
    }

    public static void ifEmpty(String description, Object[] array) {
        if (array == null || array.length == 0) {
            if (description == null) {
                description = "ifEmpty";
            }
            description += ": expected to be not empty, but is empty";
            throw new PreconditionException(description);
        }
    }

    public static void ifEmpty(Collection<?> collection) {
        ifEmpty(null, collection);
    }

    public static void ifEmpty(String description, Collection<?> collection) {
        if (collection.isEmpty()) {
            if (description == null) {
                description = "ifEmpty";
            }
            description += ": expected to be not empty, but is empty";
            throw new PreconditionException(description);
        }
    }

    public static void ifNotBlank(String string) {
        ifFalse(isBlank(string));
    }

    public static void ifNotBlank(String message, String string) {
        ifFalse(message, isBlank(string));
    }

}
