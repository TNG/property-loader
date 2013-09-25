package com.tngtech.propertyloader.impl;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class PropertyFilter {

    private final PropertyLoaderFactory propertyLoaderFactory;

    private List<PropertyLoaderFilter> filters = Lists.newArrayList();

    @Autowired
    public PropertyFilter(PropertyLoaderFactory propertyLoaderFactory){

        this.propertyLoaderFactory = propertyLoaderFactory;
    }

    public List<PropertyLoaderFilter> getFilters() {
        return filters;
    }

    public PropertyFilter withDefaultFilters() {
        return this.withVariableResolvingFilter()
                .withEnvironmentResolvingFilter()
                .withWarnIfPropertyHasToBeDefined()
                .withWarnOnSurroundingWhitespace();
    }

    public PropertyFilter withVariableResolvingFilter() {
        filters.add(propertyLoaderFactory.getVariableResolvingFilter());
        return this;
    }

    public PropertyFilter withEnvironmentResolvingFilter() {
        filters.add(propertyLoaderFactory.getEnvironmentResolvingFilter());
        return this;
    }

    public PropertyFilter withWarnIfPropertyHasToBeDefined() {
        filters.add(propertyLoaderFactory.getWarnIfPropertyHasToBeDefined());
        return this;
    }

    public PropertyFilter withWarnOnSurroundingWhitespace() {
        filters.add(propertyLoaderFactory.getWarnOnSurroundingWhitespace());
        return this;
    }

    public PropertyFilter clear() {
        filters.clear();
        return this;
    }
}
