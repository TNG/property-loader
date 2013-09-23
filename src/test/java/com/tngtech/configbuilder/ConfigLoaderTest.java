package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotationprocessors.implementations.CommandLineValueProcessor;
import com.tngtech.configbuilder.annotationprocessors.implementations.DefaultValueProcessor;
import com.tngtech.configbuilder.annotationprocessors.implementations.PropertyValueProcessor;
import com.tngtech.configbuilder.annotations.*;
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
public class ConfigLoaderTest {

    private Properties properties;
    private Properties errors;

    @Mock
    private ConfigBuilderContext context;

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
            CommandLineValue commandLineValue = Config.class.getDeclaredField("surName").getAnnotation(CommandLineValue.class);

            String[] args = new String[]{"-u", "Mueller"};
            Options options = new Options();
            options.addOption("u", true, "surName");
            CommandLineParser parser = new GnuParser();
            try {
                CommandLine commandLineArgs = parser.parse( options, args);
                CommandLineValueProcessor commandLineValueProcessor = new CommandLineValueProcessor();
                when(context.getCommandLineArgs()).thenReturn(commandLineArgs);
                String result =  commandLineValueProcessor.getValue(commandLineValue, context);
                assertEquals("Mueller", result);

            } catch (ParseException e) {}


        }
        catch (NoSuchFieldException e){}
    }

    @Test
    public void testThatPropertyValueHandlerLoadsStringFromAnnotation(){
        try{
            PropertyValue propertyValue = Config.class.getDeclaredField("helloWorld").getAnnotation(PropertyValue.class);
            Properties properties = new Properties();
            PropertyValueProcessor propertyValueProcessor = new PropertyValueProcessor();
            when(context.getProperties()).thenReturn(properties);
            properties.put("a","HelloWorld");
            String result =  propertyValueProcessor.getValue(propertyValue, context);
            assertEquals("HelloWorld",result);
        }
        catch (NoSuchFieldException e){}
    }

    @Test
    public void testThatDefaultValueHandlerLoadsStringFromAnnotation(){
        try{
            DefaultValue defaultValue = Config.class.getDeclaredField("userName").getAnnotation(DefaultValue.class);
            DefaultValueProcessor defaultValueProcessor = new DefaultValueProcessor();
            String result =  defaultValueProcessor.getValue(defaultValue, context);
            assertEquals("user",result);
        }
        catch (NoSuchFieldException e){}
    }
}
