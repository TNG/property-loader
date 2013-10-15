package com.tngtech.propertyloader.impl.interfaces;


public interface PropertyFilterContainer<T> {
    public T withDefaultFilters();

    public T withVariableResolvingFilter();

    public T withEnvironmentResolvingFilter();

    public T withWarnIfPropertyHasToBeDefined();

    public T withWarnOnSurroundingWhitespace();
}
