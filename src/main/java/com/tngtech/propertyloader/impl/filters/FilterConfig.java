package com.tngtech.propertyloader.impl.filters;

import java.util.ArrayList;
import java.util.List;

import com.tngtech.propertyloader.impl.PropertyLoaderFilter;

/**
 * Static factory for {@link com.tngtech.propertyloader.impl.PropertyLoaderFilter}
 * instances - used so the implementing classes can be package private
 * and not pop up during import search.
 */
public abstract class FilterConfig {
    public static List<PropertyLoaderFilter> defaultConfig() {
        List<PropertyLoaderFilter> result = new ArrayList<PropertyLoaderFilter>();

        result.add(new IncludeProcessing());
        result.add(new EnvironmentResolvingFilter());
        result.add(new ThrowIfPropertyHasToBeDefined());
        result.add(new VariableResolvingFilter());
        result.add(new WarnOnSurroundingWhitespace());
        result.add(new DecryptingFilter());

        return result;
    }
}
