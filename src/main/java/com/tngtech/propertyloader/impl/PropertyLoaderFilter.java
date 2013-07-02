package com.tngtech.propertyloader.impl;

import com.tngtech.infrastructure.util.OrderedProperties;

/**
 * Implementations of this interface are used to postprocess properties after raw loading.
 *
 * Examples include variable expansion, warning if keys or values look bad,
 * or de-obfuscating values.
 */
public interface PropertyLoaderFilter {
    /**
     * Applies some processing to loaded properties.
     *
     * Implementations are permitted to modify the given properties in any way,
     * including adding or removing properties. If some unacceptable problem is encountered,
     * should throw InitializationException or an appropriate subclass.
     */
    void filter(OrderedProperties props, PropertyLoaderState state);
}
