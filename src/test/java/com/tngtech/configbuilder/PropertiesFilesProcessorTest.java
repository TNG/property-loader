package com.tngtech.configbuilder;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.annotationprocessors.PropertiesFilesProcessor;
import com.tngtech.configbuilder.annotations.PropertiesFiles;
import com.tngtech.propertyloader.PropertyLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesFilesProcessorTest {

    @Mock
    private ConfigBuilderContext context;
    @Mock
    private PropertiesFiles propertiesFiles;
    @Mock
    PropertyLoader propertyLoader;

    @Test
    public void testPropertiesFilesProcessor(){

        PropertiesFilesProcessor propertiesFilesProcessor = new PropertiesFilesProcessor();

        String[] fileNames = new String[]{"file1","file2"};

        when(context.getPropertyLoader()).thenReturn(propertyLoader);
        when(propertiesFiles.value()).thenReturn(fileNames);

        propertiesFilesProcessor.configurePropertyLoader(propertiesFiles, context);

        verify(propertyLoader).withBaseNames(Lists.newArrayList(fileNames));
    }
}
