package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotationprocessors.CommandLineValueProcessor;
import com.tngtech.configbuilder.annotationprocessors.DefaultValueProcessor;
import com.tngtech.configbuilder.annotationprocessors.PropertyValueProcessor;
import com.tngtech.configbuilder.annotations.*;
import com.tngtech.configbuilder.impl.BuilderConfiguration;
import com.tngtech.propertyloader.PropertyLoader;
import org.apache.commons.cli.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfigBuilderComponentTest {

    private Properties properties;
    private Properties errors;

    @Mock
    private BuilderConfiguration builderConfiguration;

    @Mock
    private PropertyLoader propertyLoader;

    @Before
    public void setUp(){
        properties = new Properties();
        errors = new Properties();
        properties.put("thisisaproperty","");
        errors.put("thisisanerrormessage","");
    }

    @Test
    public void testThatCommandLineValueHandlerLoadsStringFromAnnotation(){
        try{
            CommandLineValue commandLineValue = TestConfig.class.getDeclaredField("surName").getAnnotation(CommandLineValue.class);

            String[] args = new String[]{"-u", "Mueller"};
            Options options = new Options();
            options.addOption("u", true, "surName");
            CommandLineParser parser = new GnuParser();
            try {
                CommandLine commandLineArgs = parser.parse( options, args);
                CommandLineValueProcessor commandLineValueProcessor = new CommandLineValueProcessor();
                when(builderConfiguration.getCommandLineArgs()).thenReturn(commandLineArgs);
                String result =  commandLineValueProcessor.getValue(commandLineValue, builderConfiguration);
                assertEquals("Mueller", result);

            } catch (ParseException e) {}


        }
        catch (NoSuchFieldException e){}
    }

    @Test
    public void testThatPropertyValueHandlerLoadsStringFromAnnotation(){
        try{
            PropertyValue propertyValue = TestConfig.class.getDeclaredField("helloWorld").getAnnotation(PropertyValue.class);
            Properties properties = new Properties();
            PropertyValueProcessor propertyValueProcessor = new PropertyValueProcessor();
            when(builderConfiguration.getProperties()).thenReturn(properties);
            properties.put("a","HelloWorld");
            String result =  propertyValueProcessor.getValue(propertyValue, builderConfiguration);
            assertEquals("HelloWorld",result);
        }
        catch (NoSuchFieldException e){}
    }

    @Test
    public void testThatDefaultValueHandlerLoadsStringFromAnnotation(){
        try{
            DefaultValue defaultValue = TestConfig.class.getDeclaredField("userName").getAnnotation(DefaultValue.class);
            DefaultValueProcessor defaultValueProcessor = new DefaultValueProcessor();
            String result =  defaultValueProcessor.getValue(defaultValue, builderConfiguration);
            assertEquals("user",result);
        }
        catch (NoSuchFieldException e){}
    }
}
