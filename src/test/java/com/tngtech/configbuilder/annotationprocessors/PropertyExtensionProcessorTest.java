package com.tngtech.configbuilder.annotationprocessors;

import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertiesFilesProcessor;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyExtension;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyExtensionProcessor;
import com.tngtech.propertyloader.PropertyLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertyExtensionProcessorTest {

    private PropertyExtensionProcessor propertyExtensionProcessor;

    @Mock
    private PropertyExtension propertyExtension;
    @Mock
    PropertyLoader propertyLoader;

    @Before
    public void setUp() throws Exception {
        propertyExtensionProcessor = new PropertyExtensionProcessor();
    }

    @Test
    public void testPropertyExtensionProcessor(){


        when(propertyExtension.value()).thenReturn("extension");

        propertyExtensionProcessor.configurePropertyLoader(propertyExtension, propertyLoader);

        verify(propertyLoader).withExtension("extension");
    }
}
