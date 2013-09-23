package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotationprocessors.implementations.CommandLineValueProcessor;
import com.tngtech.configbuilder.annotations.CommandLineValue;
import org.apache.commons.cli.CommandLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineValueProcessorTest {

    @Mock
    private ConfigBuilderContext context;
    @Mock
    private CommandLine commandLine;
    @Mock
    private CommandLineValue commandLineValue;

    @Test
    public void testCommandLineValueProcessor(){

        CommandLineValueProcessor commandLineValueProcessor = new CommandLineValueProcessor();

        when(context.getCommandLineArgs()).thenReturn(commandLine);
        when(commandLineValue.value()).thenReturn("value");
        when(commandLine.getOptionValue("value")).thenReturn("passed");
        assertEquals("passed", commandLineValueProcessor.getValue(commandLineValue, context));
    }
}
