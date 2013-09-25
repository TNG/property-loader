package com.tngtech.propertyloader.impl;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.impl.interfaces.PropertyFilter;
import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class DefaultPropertyFilter implements PropertyFilter<DefaultPropertyFilter> {

    private final PropertyLoaderFactory propertyLoaderFactory;

    private List<PropertyLoaderFilter> filters = Lists.newArrayList();

    @Autowired
    public DefaultPropertyFilter(PropertyLoaderFactory propertyLoaderFactory){

        this.propertyLoaderFactory = propertyLoaderFactory;
    }

    public List<PropertyLoaderFilter> getFilters() {
        return filters;
    }

    public DefaultPropertyFilter withDefaultFilters() {
        return this.withVariableResolvingFilter()
                .withEnvironmentResolvingFilter()
                .withWarnIfPropertyHasToBeDefined()
                .withWarnOnSurroundingWhitespace();
    }

    public DefaultPropertyFilter withVariableResolvingFilter() {
        filters.add(propertyLoaderFactory.getVariableResolvingFilter());
        return this;
    }

    public DefaultPropertyFilter withEnvironmentResolvingFilter() {
        filters.add(propertyLoaderFactory.getEnvironmentResolvingFilter());
        return this;
    }

    public DefaultPropertyFilter withWarnIfPropertyHasToBeDefined() {
        filters.add(propertyLoaderFactory.getWarnIfPropertyHasToBeDefined());
        return this;
    }

    public DefaultPropertyFilter withWarnOnSurroundingWhitespace() {
        filters.add(propertyLoaderFactory.getWarnOnSurroundingWhitespace());
        return this;
    }

    public DefaultPropertyFilter clear() {
        filters.clear();
        return this;
    }
}
