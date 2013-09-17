package com.tngtech.configbuilder.impl;


import java.util.Collection;

public interface FieldValueProvider<T> {
    public T getValue(String optionValue);
}
