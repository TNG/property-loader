package com.tngtech.configbuilder.util;

import com.google.common.collect.Sets;
import com.tngtech.configbuilder.annotation.valueextractor.CommandLineValue;
import com.tngtech.configbuilder.annotation.valueextractor.DefaultValue;
import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineHelperTest {

    private static class TestConfig {
        @CommandLineValue(shortOpt = "u", longOpt = "user")
        public String testString;
    }

    private CommandLineHelper commandLineHelper;
    private String[] args = null;

    @Mock
    private Options options;
    @Mock
    private CommandLineParser parser;
    @Mock
    private CommandLine commandLine;
    @Mock
    private BeanFactory beanFactory;
    @Mock
    private AnnotationHelper annotationHelper;
    @Mock
    private ErrorMessageSetup errorMessageSetup;


    @Before
    public void setUp() throws Exception {
        commandLineHelper = new CommandLineHelper(beanFactory, annotationHelper, errorMessageSetup);

        Set<Field> fields = Sets.newHashSet(TestConfig.class.getDeclaredField("testString"));

        when(beanFactory.getBean(CommandLineParser.class)).thenReturn(parser);
        when(beanFactory.getBean(Options.class)).thenReturn(options);
        when(annotationHelper.getFieldsAnnotatedWith(TestConfig.class, CommandLineValue.class)).thenReturn(fields);
        when(parser.parse(options, args)).thenReturn(commandLine);
    }

    @Test
    public void testGetCommandLine() throws Exception {
        ArgumentCaptor<Option> captor = ArgumentCaptor.forClass(Option.class);
        assertEquals(commandLine,commandLineHelper.getCommandLine(TestConfig.class, args));
        verify(options, times(1)).addOption(captor.capture());
        verify(parser).parse(options,args);
        Option option = captor.getValue();
        assertEquals("user",option.getLongOpt());
    }
}
