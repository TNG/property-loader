package com.tngtech.configbuilder;

public interface FieldValueProvider<T> {
    public T getValue(String optionValue);
}
