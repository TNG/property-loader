package com.tngtech.configbuilder.interfaces;

public interface FieldValueProvider<T> {
    public T getValue(String optionValue);
}
