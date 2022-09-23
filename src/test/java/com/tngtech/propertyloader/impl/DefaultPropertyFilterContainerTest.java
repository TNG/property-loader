package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.filters.EnvironmentResolvingFilter;
import com.tngtech.propertyloader.impl.filters.ThrowIfPropertyHasToBeDefined;
import com.tngtech.propertyloader.impl.filters.VariableResolvingFilter;
import com.tngtech.propertyloader.impl.filters.WarnOnSurroundingWhitespace;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPropertyFilterContainerTest {

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

    @Before
    public void setUp() throws MalformedURLException {
        when(propertyLoaderFactory.getVariableResolvingFilter()).thenReturn(variableResolvingFilter);
        when(propertyLoaderFactory.getEnvironmentResolvingFilter()).thenReturn(environmentResolvingFilter);
        when(propertyLoaderFactory.getWarnOnSurroundingWhitespace()).thenReturn(warnOnSurroundingWhitespace);
        when(propertyLoaderFactory.getWarnIfPropertyHasToBeDefined()).thenReturn(throwIfPropertyHasToBeDefined);
    }

    @Test
    public void testGetFilters() {

    }

    @Test
    public void testWithDefaultFilters() {

    }

    @Test
    public void testWithVariableResolvingFilter() {
        assertThat(propertyFilter.withVariableResolvingFilter()).isSameAs(propertyFilter);
        assertThat(propertyFilter.getFilters()).contains(variableResolvingFilter);
    }

    @Test
    public void testWithEnvironmentResolvingFilter() {
        assertThat(propertyFilter.withEnvironmentResolvingFilter()).isSameAs(propertyFilter);
        assertThat(propertyFilter.getFilters()).contains(environmentResolvingFilter);
    }

    @Test
    public void testWithWarnIfPropertyHasToBeDefined() {
        assertThat(propertyFilter.withWarnIfPropertyHasToBeDefined()).isSameAs(propertyFilter);
        assertThat(propertyFilter.getFilters()).contains(throwIfPropertyHasToBeDefined);
    }

    @Test
    public void testWithWarnOnSurroundingWhitespace() {
        assertThat(propertyFilter.withWarnOnSurroundingWhitespace()).isSameAs(propertyFilter);
        assertThat(propertyFilter.getFilters()).contains(warnOnSurroundingWhitespace);
    }

    @Test
    public void testClear() {

    }
}
