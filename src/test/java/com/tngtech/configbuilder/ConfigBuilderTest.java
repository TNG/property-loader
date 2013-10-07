package com.tngtech.configbuilder;

import com.tngtech.configbuilder.configuration.BuilderConfiguration;
import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.configbuilder.testclasses.TestConfig;
import com.tngtech.configbuilder.util.*;
import com.tngtech.propertyloader.PropertyLoader;
import com.tngtech.propertyloader.impl.DefaultPropertyLocationContainer;
import com.tngtech.propertyloader.impl.DefaultPropertySuffixContainer;
import org.apache.commons.cli.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConfigBuilderTest {

    private ConfigBuilder<TestConfig> configBuilder;
    private Properties properties;

    @Mock
    private BuilderConfiguration builderConfiguration;
    @Mock
    private AnnotationHelper annotationHelper;
    @Mock
    private CommandLineHelper commandLineHelper;
    @Mock
    private JSRValidator jsrValidator;
    @Mock
    private FieldSetter fieldSetter;
    @Mock
    private PropertyLoaderConfigurator propertyLoaderConfigurator;
    @Mock
    private ErrorMessageSetup errorMessageSetup;
    @Mock
    private PropertyLoader propertyLoader;
    @Mock
    private DefaultPropertySuffixContainer propertySuffix;
    @Mock
    private DefaultPropertyLocationContainer propertyLocation;
    @Mock
    private ConstructionHelper constructionHelper;



    @Before
    public void setUp(){
        configBuilder = new ConfigBuilder<>(TestConfig.class, builderConfiguration, propertyLoaderConfigurator, commandLineHelper, jsrValidator, fieldSetter, errorMessageSetup, constructionHelper);
        properties = new Properties();

        when(propertyLoader.getSuffixes()).thenReturn(propertySuffix);
        when(propertyLoader.getLocations()).thenReturn(propertyLocation);
        when(propertyLocation.atDefaultLocations()).thenReturn(propertyLocation);
        when(propertySuffix.addDefaultSuffixes()).thenReturn(propertySuffix);
    }

    //calls to static methods on OptionBuilder
    @Test
    public void testWithCommandLineArguments() throws ParseException {
        String[] args = new String[]{"-u", "Mueller"};

        configBuilder.withCommandLineArgs(args);

        //verify(options,times(2)).addOption(Matchers.any(Option.class));
        //verify(builderConfiguration).setCommandLine(commandLine);
    }

}
