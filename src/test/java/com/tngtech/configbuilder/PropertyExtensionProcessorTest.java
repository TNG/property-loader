package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotationprocessors.PropertyExtensionProcessor;
import com.tngtech.configbuilder.annotations.PropertyExtension;
import com.tngtech.propertyloader.PropertyLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertyExtensionProcessorTest {

    @Mock
    private PropertyExtension propertyExtension;
    @Mock
    PropertyLoader propertyLoader;

    @Test
    public void testPropertyExtensionProcessor(){

        PropertyExtensionProcessor propertyExtensionProcessor = new PropertyExtensionProcessor();

        when(propertyExtension.value()).thenReturn("extension");

        propertyExtensionProcessor.configurePropertyLoader(propertyExtension, propertyLoader);

        verify(propertyLoader).withExtension("extension");
    }
}
