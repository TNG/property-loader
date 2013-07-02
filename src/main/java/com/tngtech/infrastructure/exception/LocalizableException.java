package com.tngtech.infrastructure.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Exception that provides storage for a localizable error message as well as rich error details.
 */
public abstract class LocalizableException extends RuntimeException implements Localizable {
    private static final long serialVersionUID = -7774037514564960116L;
    private final Map<String, Object> errorDetails = new HashMap<String, Object>();
    private final Object[] i18nValues;

    public LocalizableException() {
        super();
        i18nValues = new Object[0];
    }

    public LocalizableException(String i18nKey) {
        super(i18nKey);
        i18nValues = new Object[0];
    }

    public LocalizableException(String i18nKey, Object... i18nValues) {
        super(i18nKey);
        this.i18nValues = i18nValues;
    }

    public LocalizableException(String i18nKey, Throwable cause) {
        super(i18nKey, cause);
        i18nValues = new Object[0];
    }

    public LocalizableException(String i18nKey, Throwable cause, Object... i18nValues) {
        super(i18nKey, cause);
        this.i18nValues = i18nValues;
    }

    public LocalizableException(Throwable cause) {
        super(cause);
        i18nValues = new Object[0];
    }

    @Override
    public String getI18nKey() {
        return getMessage();
    }

    @Override
    public Object[] getI18nValues() {
        return i18nValues;
    }

    public Map<String, Object> getErrorDetails() {
        return errorDetails;
    }

    @Override
    public String toString() {
        StringBuilder exceptionString = new StringBuilder(super.toString());

        if (!errorDetails.isEmpty()) {
            exceptionString.append(" [Exception details: ");

            int i = 0;
            for (Object i18nValue : i18nValues) {
                exceptionString.append(" {").append(i).append("}=").append(i18nValue);
                i++;
            }

            for (String key : errorDetails.keySet()) {
                exceptionString.append(" ").append(key).append("=").append(errorDetails.get(key));
            }

            exceptionString.append(" ]");
        }

        return exceptionString.toString();
    }

}
