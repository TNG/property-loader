package com.tngtech.propertyloader.impl.interfaces;

import java.util.Properties;

/**
 * <p>
 *     Implementations of this interface are used to postprocess properties after raw loading.
 * </p>
 *
 * <p>
 *     Examples include variable expansion, warning if keys or values look bad, or de-obfuscating values.
 * </p>
 */
public interface PropertyLoaderFilter {

    void filter(Properties properties);
}
