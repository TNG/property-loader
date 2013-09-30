package com.tngtech.configbuilder;

import com.tngtech.propertyloader.PropertyLoader;
import com.tngtech.propertyloader.impl.DefaultPropertyLocationContainer;
import com.tngtech.propertyloader.impl.DefaultPropertySuffixContainer;
import org.apache.commons.cli.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConfigBuilderTest {

    private ConfigBuilder<Config> configBuilder;
    private Properties properties;


    @Mock
    private BuilderConfiguration builderConfiguration;
    @Mock
    private AnnotationUtils annotationUtils;
    @Mock
    private CommandLineHelper commandLineHelper;
    @Mock
    private JSRValidator jsrValidator;
    @Mock
    private FieldSetter fieldSetter;


    @Mock
    private MiscFactory miscFactory;

    @Mock
    private PropertyLoader propertyLoader;

    @Mock
    private DefaultPropertySuffixContainer propertySuffix;

    @Mock
    private DefaultPropertyLocationContainer propertyLocation;



    @Before
    public void setUp(){
        configBuilder = new ConfigBuilder<>(Config.class, builderConfiguration, annotationUtils, commandLineHelper, jsrValidator, fieldSetter, miscFactory);
        properties = new Properties();

        when(miscFactory.createPropertyLoader()).thenReturn(propertyLoader);
        when(propertyLoader.getSuffixes()).thenReturn(propertySuffix);
        when(propertyLoader.getLocations()).thenReturn(propertyLocation);
        when(propertyLocation.atDefaultLocations()).thenReturn(propertyLocation);
        when(propertySuffix.addDefaultSuffixes()).thenReturn(propertySuffix);
    }

    //calls to static methods on OptionBuilder
    @Test
    public void testWithCommandLineArguments() throws ParseException {
        String[] args = new String[]{"-u", "Mueller"};

        Options options = mock(Options.class);
        when(miscFactory.createOptions()).thenReturn(options);

        CommandLineParser parser = mock(CommandLineParser.class);
        when(miscFactory.createCommandLineParser()).thenReturn(parser);

        CommandLine commandLine = mock(CommandLine.class);
        when(parser.parse(options, args)).thenReturn(commandLine);

        configBuilder.withCommandLineArgs(args);

        verify(options,times(2)).addOption(Matchers.any(Option.class));
        verify(builderConfiguration).setCommandLineArgs(commandLine);
    }

}
