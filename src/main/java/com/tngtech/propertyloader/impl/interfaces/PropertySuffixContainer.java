package com.tngtech.propertyloader.impl.interfaces;


import java.util.List;

public interface PropertySuffixContainer<T> {
    public T addUserName();

    public T addLocalHostNames();

    public T addString(String suffix);

    public T addSuffixList(List<String> suffixes);

    public T addDefaultSuffixes();
}
