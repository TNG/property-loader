package com.tngtech.propertyloader.impl;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.impl.interfaces.PropertyFilterContainer;
import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class DefaultPropertyFilterContainer implements PropertyFilterContainer<DefaultPropertyFilterContainer> {

    private final PropertyLoaderFactory propertyLoaderFactory;

    private List<PropertyLoaderFilter> filters = Lists.newArrayList();

    @Autowired
    public DefaultPropertyFilterContainer(PropertyLoaderFactory propertyLoaderFactory){

        this.propertyLoaderFactory = propertyLoaderFactory;
    }

    public List<PropertyLoaderFilter> getFilters() {
        return filters;
    }

    public DefaultPropertyFilterContainer withDefaultFilters() {
        return this.withVariableResolvingFilter()
                .withEnvironmentResolvingFilter()
                .withWarnIfPropertyHasToBeDefined()
                .withWarnOnSurroundingWhitespace();
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
