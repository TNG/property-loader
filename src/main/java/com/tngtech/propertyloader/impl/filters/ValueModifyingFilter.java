package com.tngtech.propertyloader.impl.filters;

import java.util.Map;

import com.tngtech.infrastructure.util.OrderedProperties;
import com.tngtech.propertyloader.impl.PropertyLoaderFilter;
import com.tngtech.propertyloader.impl.PropertyLoaderState;

abstract class ValueModifyingFilter implements PropertyLoaderFilter {
    @Override
    public void filter(OrderedProperties props, PropertyLoaderState state) {
        for (Map.Entry<String, String> entry : props.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value != null) {
                String newValue = filterValue(key, value);
                entry.setValue(newValue);
            }
        }
    }

    protected abstract String filterValue(String key, String value);
}
