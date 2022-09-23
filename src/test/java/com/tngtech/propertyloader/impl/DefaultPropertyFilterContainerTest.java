package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.filters.EnvironmentResolvingFilter;
import com.tngtech.propertyloader.impl.filters.ThrowIfPropertyHasToBeDefined;
import com.tngtech.propertyloader.impl.filters.VariableResolvingFilter;
import com.tngtech.propertyloader.impl.filters.WarnOnSurroundingWhitespace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DefaultPropertyFilterContainerTest {

    @InjectMocks
    private DefaultPropertyFilterContainer propertyFilter;

    @Mock
    private PropertyLoaderFactory propertyLoaderFactory;
    @Mock
    private VariableResolvingFilter variableResolvingFilter;
    @Mock
    private EnvironmentResolvingFilter environmentResolvingFilter;
    @Mock
    private WarnOnSurroundingWhitespace warnOnSurroundingWhitespace;
    @Mock
    private ThrowIfPropertyHasToBeDefined throwIfPropertyHasToBeDefined;

    @BeforeEach
    void setUp() throws MalformedURLException {
        when(propertyLoaderFactory.getVariableResolvingFilter()).thenReturn(variableResolvingFilter);
        when(propertyLoaderFactory.getEnvironmentResolvingFilter()).thenReturn(environmentResolvingFilter);
        when(propertyLoaderFactory.getWarnOnSurroundingWhitespace()).thenReturn(warnOnSurroundingWhitespace);
        when(propertyLoaderFactory.getWarnIfPropertyHasToBeDefined()).thenReturn(throwIfPropertyHasToBeDefined);
    }

    @Test
    void testGetFilters() {

    }

    @Test
    void testWithDefaultFilters() {

    }

    @Test
    void testWithVariableResolvingFilter() {
        assertThat(propertyFilter.withVariableResolvingFilter()).isSameAs(propertyFilter);
        assertThat(propertyFilter.getFilters()).contains(variableResolvingFilter);
    }

    @Test
    void testWithEnvironmentResolvingFilter() {
        assertThat(propertyFilter.withEnvironmentResolvingFilter()).isSameAs(propertyFilter);
        assertThat(propertyFilter.getFilters()).contains(environmentResolvingFilter);
    }

    @Test
    void testWithWarnIfPropertyHasToBeDefined() {
        assertThat(propertyFilter.withWarnIfPropertyHasToBeDefined()).isSameAs(propertyFilter);
        assertThat(propertyFilter.getFilters()).contains(throwIfPropertyHasToBeDefined);
    }

    @Test
    void testWithWarnOnSurroundingWhitespace() {
        assertThat(propertyFilter.withWarnOnSurroundingWhitespace()).isSameAs(propertyFilter);
        assertThat(propertyFilter.getFilters()).contains(warnOnSurroundingWhitespace);
    }

    @Test
    void testClear() {

    }
}
