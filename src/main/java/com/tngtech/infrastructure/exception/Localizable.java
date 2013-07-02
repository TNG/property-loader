package com.tngtech.infrastructure.exception;

public interface Localizable {
    String getI18nKey();
    Object[] getI18nValues();
}
