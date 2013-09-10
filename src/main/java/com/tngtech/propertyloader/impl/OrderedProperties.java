package com.tngtech.propertyloader.impl;

import java.util.*;

public class OrderedProperties {
    private Map<String, String> _propertyMap;

    public OrderedProperties() {
        this(new LinkedHashMap<String, String>());
    }

    public OrderedProperties(Map<String,String> propertyMap) {
        _propertyMap = new LinkedHashMap<String, String>(propertyMap);
    }

    public String getProperty(String key) {
        Object value = _propertyMap.get(key);
        return (String) value;
    }

    public String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            return value;
        }
        return defaultValue;
    }

    public void setProperty(String key, String value) {
        _propertyMap.put(key, value);
    }

    public Map<String,String> getPropertyMap() {
        return _propertyMap;
    }

    public Properties asProperties() {
        Properties properties = new Properties();
        properties.putAll(_propertyMap);
        return properties;
    }

    public void addAll(Properties properties) {
        _propertyMap.putAll((Map) properties);
    }

    public void addAll(OrderedProperties includeProperties) {
        _propertyMap.putAll(includeProperties._propertyMap);
    }

    public int size() {
        return _propertyMap.size();
    }

    public Set<Map.Entry<String, String>> entrySet() {
        return _propertyMap.entrySet();
    }

    public Enumeration<?> propertyNames() {
        return Collections.enumeration(_propertyMap.keySet());
    }
}
