package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotationhandlers.AnnotationProcessor;
import com.tngtech.configbuilder.impl.AnnotationHelper;
import com.tngtech.configbuilder.impl.ConfigBuilderContext;
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConfigBuilderTest {

    private ConfigBuilder<Config> configBuilder;
    private Properties properties;
    private Properties errors;

    @Mock
    private AnnotationProcessor annotationProcessor;

    @Mock
    private ConfigBuilderContext builderContext;

    @Mock
    private MiscFactory miscFactory;

    @Mock
    private AnnotationHelper annotationHelper;


    @Before
    public void setUp(){
        configBuilder = new ConfigBuilder<>(annotationProcessor, builderContext, miscFactory, annotationHelper);
        properties = new Properties();

        when(annotationHelper.loadPropertiesFromAnnotation(Matchers.any(Annotation.class))).thenReturn(properties);
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

        configBuilder.forClass(Config.class);

        verify(annotationHelper, times(2)).loadPropertiesFromAnnotation(Matchers.any(Annotation.class));
        verify(builderContext).setProperties(properties);
    }

}
