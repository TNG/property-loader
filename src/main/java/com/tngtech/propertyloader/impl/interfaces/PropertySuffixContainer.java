package com.tngtech.propertyloader.impl.interfaces;

import java.util.List;

public interface PropertySuffixContainer<T> {

    T addUserName();

    T addLocalHostNames();

    T addString(String suffix);

    T addSuffixList(List<String> suffixes);

    T addDefaultSuffixes();
}
