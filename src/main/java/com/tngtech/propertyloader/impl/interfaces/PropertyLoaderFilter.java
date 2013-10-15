package com.tngtech.propertyloader.impl.interfaces;

import java.util.Properties;

/**
 * Implementations of this interface are used to postprocess properties after raw loading.
 * <p/>
 * Examples include variable expansion, warning if keys or values look bad, or de-obfuscating values.
 */
public interface PropertyLoaderFilter {

    public void filter(Properties properties);
}
