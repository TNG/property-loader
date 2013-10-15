package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.filters.EnvironmentResolvingFilter;
import com.tngtech.propertyloader.impl.filters.ThrowIfPropertyHasToBeDefined;
import com.tngtech.propertyloader.impl.filters.VariableResolvingFilter;
import com.tngtech.propertyloader.impl.filters.WarnOnSurroundingWhitespace;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPropertyFilterContainerTest {

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
        propertyFilter = new DefaultPropertyFilterContainer(propertyLoaderFactory);
        when(propertyLoaderFactory.getVariableResolvingFilter()).thenReturn(variableResolvingFilter);
        when(propertyLoaderFactory.getEnvironmentResolvingFilter()).thenReturn(environmentResolvingFilter);
        when(propertyLoaderFactory.getWarnOnSurroundingWhitespace()).thenReturn(warnOnSurroundingWhitespace);
        when(propertyLoaderFactory.getWarnIfPropertyHasToBeDefined()).thenReturn(throwIfPropertyHasToBeDefined);

    }

    @Test
    public void testGetFilters() throws Exception {

    }

    @Test
    public void testWithDefaultFilters() throws Exception {

    }

    @Test
    public void testWithVariableResolvingFilter() throws Exception {
        assertEquals(propertyFilter, propertyFilter.withVariableResolvingFilter());
        assertTrue(propertyFilter.getFilters().contains(variableResolvingFilter));
    }

    @Test
    public void testWithEnvironmentResolvingFilter() throws Exception {
        assertEquals(propertyFilter, propertyFilter.withEnvironmentResolvingFilter());
        assertTrue(propertyFilter.getFilters().contains(environmentResolvingFilter));
    }

    @Test
    public void testWithWarnIfPropertyHasToBeDefined() throws Exception {
        assertEquals(propertyFilter, propertyFilter.withWarnIfPropertyHasToBeDefined());
        assertTrue(propertyFilter.getFilters().contains(throwIfPropertyHasToBeDefined));
    }

    @Test
    public void testWithWarnOnSurroundingWhitespace() throws Exception {
        assertEquals(propertyFilter, propertyFilter.withWarnOnSurroundingWhitespace());
        assertTrue(propertyFilter.getFilters().contains(warnOnSurroundingWhitespace));
    }

    @Test
    public void testClear() throws Exception {

    }
}
