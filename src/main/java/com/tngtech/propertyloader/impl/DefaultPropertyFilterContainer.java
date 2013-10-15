package com.tngtech.propertyloader.impl;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.impl.interfaces.PropertyFilterContainer;
import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderFilter;

import java.util.List;

public class DefaultPropertyFilterContainer implements PropertyFilterContainer<DefaultPropertyFilterContainer> {

    private final PropertyLoaderFactory propertyLoaderFactory;

    private List<PropertyLoaderFilter> filters = Lists.newArrayList();

    public DefaultPropertyFilterContainer(PropertyLoaderFactory propertyLoaderFactory) {

        this.propertyLoaderFactory = propertyLoaderFactory;
    }

    public List<PropertyLoaderFilter> getFilters() {
        return filters;
    }

    public DefaultPropertyFilterContainer withDefaultFilters() {
        return this.withVariableResolvingFilter()
                .withEnvironmentResolvingFilter()
                .withWarnIfPropertyHasToBeDefined()
                .withWarnOnSurroundingWhitespace()
                .withDecryptingFilter();
    }

    public DefaultPropertyFilterContainer withDecryptingFilter() {
        filters.add(propertyLoaderFactory.getDecryptingFilter());
        return this;
    }

    public DefaultPropertyFilterContainer withVariableResolvingFilter() {
        filters.add(propertyLoaderFactory.getVariableResolvingFilter());
        return this;
    }

    public DefaultPropertyFilterContainer withEnvironmentResolvingFilter() {
        filters.add(propertyLoaderFactory.getEnvironmentResolvingFilter());
        return this;
    }

    public DefaultPropertyFilterContainer withWarnIfPropertyHasToBeDefined() {
        filters.add(propertyLoaderFactory.getWarnIfPropertyHasToBeDefined());
        return this;
    }

    public DefaultPropertyFilterContainer withWarnOnSurroundingWhitespace() {
        filters.add(propertyLoaderFactory.getWarnOnSurroundingWhitespace());
        return this;
    }

    public DefaultPropertyFilterContainer clear() {
        filters.clear();
        return this;
    }
}
