package com.tngtech.configbuilder.impl;

public interface FieldValueProvider<T> {
    public T getValue(String optionValue);
}
