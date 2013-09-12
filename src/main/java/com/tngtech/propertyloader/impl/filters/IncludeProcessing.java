package com.tngtech.propertyloader.impl.filters;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.PropertyLoader;
import com.tngtech.propertyloader.impl.PropertyLoaderFilter;

import java.util.List;
import java.util.Map;
import java.util.Properties;

class IncludeProcessing implements PropertyLoaderFilter {
    private static final String INCLUDE_KEY = "%%include%%";
    private PropertyLoader propertyLoader;

    IncludeProcessing(PropertyLoader propertyLoader) {
        this.propertyLoader = propertyLoader;
    }

    @Override
    public void filter(Properties properties) {

        List<String> includes = collectIncludes(properties);

        while (includes.size() > 0) {

            includes = Lists.newArrayList();
            for (String include : includes) {
                Properties includedProperties = propertyLoader.loadProperties(include, propertyLoader.getExtension());
                includes.addAll(collectIncludes(includedProperties));
            }
        }
    }

    private List<String> collectIncludes(Properties properties) {

        List<String> collectedIncludes = Lists.newArrayList();

        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            if (INCLUDE_KEY.equals(key)) {
                collectedIncludes.add(value);
            }
        }
        return collectedIncludes;
    }
}