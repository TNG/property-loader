package com.tngtech.configbuilder.annotation.propertyloaderconfiguration;

import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertiesFilesProcessor;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyLocations;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyLocationsProcessor;
import com.tngtech.propertyloader.PropertyLoader;
import com.tngtech.propertyloader.impl.DefaultPropertyLocationContainer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertyLocationsProcessorTest {

    private PropertyLocationsProcessor propertyLocationsProcessor;

    @Mock
    private PropertyLocations propertyLocations;
    @Mock
    DefaultPropertyLocationContainer propertyLocation;
    @Mock
    PropertyLoader propertyLoader;

    @Before
    public void setUp() throws Exception {
        propertyLocationsProcessor = new PropertyLocationsProcessor();
    }

    @Test
    public void testPropertyLocationsProcessor(){

        String[] dirs = new String[]{"dir1","dir2"};
        Class[] classes = new Class[]{this.getClass(), Object.class};

        when(propertyLoader.getLocations()).thenReturn(propertyLocation);
        when(propertyLocations.directories()).thenReturn(dirs);
        when(propertyLocations.resourcesForClasses()).thenReturn(classes);
        when(propertyLocations.fromClassLoader()).thenReturn(false);

        propertyLocationsProcessor.configurePropertyLoader(propertyLocations, propertyLoader);

        verify(propertyLocation).clear();
        verify(propertyLoader).atDirectory("dir1");
        verify(propertyLoader).atDirectory("dir2");
        verify(propertyLoader).atRelativeToClass(this.getClass());
        verify(propertyLoader).atRelativeToClass(Object.class);
    }

    @Test
    public void testPropertyLocationsProcessorWithClassLoader(){

        when(propertyLoader.getLocations()).thenReturn(propertyLocation);
        when(propertyLocations.directories()).thenReturn(new String[]{});
        when(propertyLocations.resourcesForClasses()).thenReturn(new Class[]{});
        when(propertyLocations.fromClassLoader()).thenReturn(true);

        propertyLocationsProcessor.configurePropertyLoader(propertyLocations, propertyLoader);

        verify(propertyLocation).clear();
        verify(propertyLoader).atContextClassPath();
    }
}
