package com.tngtech.propertyloader.impl.interfaces;

public interface PropertyFilterContainer<T> {

    T withDefaultFilters();

    T withVariableResolvingFilter();

    T withEnvironmentResolvingFilter();

    T withWarnIfPropertyHasToBeDefined();

    T withWarnOnSurroundingWhitespace();
}
