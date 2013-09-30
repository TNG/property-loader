package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotationprocessors.PropertySuffixProcessor;
import com.tngtech.configbuilder.annotations.PropertySuffixes;
import com.tngtech.propertyloader.PropertyLoader;
import com.tngtech.propertyloader.impl.DefaultPropertySuffixContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertySuffixProcessorTest {

    @Mock
    private PropertySuffixes propertySuffixes;
    @Mock
    DefaultPropertySuffixContainer propertySuffix;
    @Mock
    PropertyLoader propertyLoader;

    @Test
    public void testPropertySuffixProcessor(){

        PropertySuffixProcessor propertySuffixProcessor = new PropertySuffixProcessor();

        String[] suffixes = new String[]{"suffix1","suffix2"};

        when(propertyLoader.getSuffixes()).thenReturn(propertySuffix);
        when(propertySuffixes.extraSuffixes()).thenReturn(suffixes);
        when(propertySuffixes.hostNames()).thenReturn(true);

        propertySuffixProcessor.configurePropertyLoader(propertySuffixes, propertyLoader);

        verify(propertySuffix).clear();
        verify(propertySuffix).addString("suffix1");
        verify(propertySuffix).addString("suffix2");
        verify(propertySuffix).addLocalHostNames();
    }
}
