package com.tngtech.configbuilder.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tngtech.configbuilder.TestConfig;
import com.tngtech.configbuilder.annotations.CommandLineValue;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineHelperTest {

    private CommandLineHelper commandLineHelper;
    private String[] args = null;

    @Mock
    private Options options;
    @Mock
    private CommandLineParser parser;
    @Mock
    private CommandLine commandLine;
    @Mock
    private MiscFactory miscFactory;
    @Mock
    private AnnotationUtils annotationUtils;


    @Before
    public void setUp() throws Exception {
        commandLineHelper = new CommandLineHelper(miscFactory,annotationUtils);

        Set<Field> fields = Sets.newHashSet();
        fields.add(TestConfig.class.getDeclaredField("surName"));

        when(miscFactory.createCommandLineParser()).thenReturn(parser);
        when(miscFactory.createOptions()).thenReturn(options);
        when(annotationUtils.getFieldsAnnotatedWith(TestConfig.class, CommandLineValue.class)).thenReturn(fields);
        when(parser.parse(options, args)).thenReturn(commandLine);
    }

    @Test
    public void testGetCommandLine() throws Exception {
        assertEquals(commandLine,commandLineHelper.getCommandLine(TestConfig.class, args));
        verify(options, times(1)).addOption(Matchers.any(Option.class));
        verify(parser).parse(options,args);
    }
}
