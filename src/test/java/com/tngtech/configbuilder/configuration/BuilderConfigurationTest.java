package com.tngtech.configbuilder.configuration;

import com.tngtech.configbuilder.annotation.valueextractor.CommandLineValue;
import com.tngtech.configbuilder.annotation.valueextractor.DefaultValue;
import com.tngtech.configbuilder.annotation.valueextractor.PropertyValue;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;


public class BuilderConfigurationTest {

    private BuilderConfiguration builderConfiguration;

    @Before
    public void setUp() throws Exception {
        builderConfiguration = new BuilderConfiguration();
    }

    @Test
    public void testGetCommandLineArgs() throws Exception {
        assertEquals(null,builderConfiguration.getCommandLine());
    }

    @Test
    public void testGetProperties() throws Exception {
        assertEquals(new Properties(),builderConfiguration.getProperties());
    }

    @Test
    public void testSetProperties() throws Exception {

    }

    @Test
    public void testSetCommandLineArgs() throws Exception {

    }

    @Test
    public void testSetAnnotationOrder() throws Exception {

    }

    @Test
    public void testGetAnnotationOrder() throws Exception {

    }
}
