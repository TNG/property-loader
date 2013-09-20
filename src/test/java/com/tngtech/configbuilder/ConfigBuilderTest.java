package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotationhandlers.AnnotationProcessor;
import com.tngtech.configbuilder.annotations.PropertiesFile;
import com.tngtech.configbuilder.impl.ConfigBuilderContext;
import com.tngtech.propertyloader.PropertyLoader;
import com.tngtech.propertyloader.impl.PropertyLocation;
import com.tngtech.propertyloader.impl.PropertySuffix;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConfigBuilderTest {

    private ConfigBuilder<Config> configBuilder;
    private Properties properties;

    @Mock
    private AnnotationProcessor annotationProcessor;

    @Mock
    private ConfigBuilderContext builderContext;

    @Mock
    private MiscFactory miscFactory;

    @Mock
    private PropertyLoader propertyLoader;

    @Mock
    private PropertySuffix propertySuffix;

    @Mock
    private PropertyLocation propertyLocation;



    @Before
    public void setUp(){
        configBuilder = new ConfigBuilder<>(annotationProcessor, builderContext, miscFactory);
        properties = new Properties();

        when(builderContext.getPropertyLoader()).thenReturn(propertyLoader);
        when(miscFactory.createPropertyLoader()).thenReturn(propertyLoader);
        when(propertyLoader.getSuffixes()).thenReturn(propertySuffix);
        when(propertyLoader.getLocations()).thenReturn(propertyLocation);
        when(propertyLocation.atDefaultLocations()).thenReturn(propertyLocation);
        when(propertySuffix.addDefaultConfig()).thenReturn(propertySuffix);
    }

    @Test
    public void testWithCommandLineArguments() throws ParseException {
        String[] args = new String[]{"-u", "Mueller"};

        Options options = mock(Options.class);
        when(miscFactory.createOptions()).thenReturn(options);

        CommandLineParser parser = mock(CommandLineParser.class);
        when(miscFactory.createCommandLineParser()).thenReturn(parser);

        CommandLine commandLine = mock(CommandLine.class);
        when(parser.parse(options, args)).thenReturn(commandLine);

        configBuilder.forClass(Config.class).withCommandLineArgs(args);

        verify(options).addOption("u", true, "");
        verify(options).addOption("p", true, "");
        verify(builderContext).setCommandLineArgs(commandLine);
    }

    @Test
    public void testThatForClassSetsClassToConfigBuilderAndReturnsConfigBuilder(){
        assertEquals(configBuilder, configBuilder.forClass(Config.class));
        assertEquals(configBuilder.getConfigClass(), Config.class);
    }


    @Test
    public void testThatForClassGetsFields(){
        Field[] configFields = Config.class.getDeclaredFields();
        configBuilder.forClass(Config.class);
        for(Field field : configFields)
        {
            assertTrue(configBuilder.getFields().contains(field));
        }
    }


    @Test
    public void testThatForClassLoadsProperties(){


    }

}
