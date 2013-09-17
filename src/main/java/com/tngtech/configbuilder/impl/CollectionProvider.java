package com.tngtech.configbuilder.impl;


import java.util.Collection;

public interface CollectionProvider<T> {
    public Collection<T> getValues(String optionValue);
}
