package com.tngtech.propertyloader.impl.filters;


import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderFilter;

import java.util.Map;
import java.util.Properties;

public abstract class ValueModifyingFilter implements PropertyLoaderFilter {
    public void filter(Properties properties) {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            if (value != null) {
                String newValue = filterValue(key, value, properties);
                entry.setValue(newValue);
            }
        }
    }

    protected abstract String filterValue(String key, String value, Properties properties);
}
