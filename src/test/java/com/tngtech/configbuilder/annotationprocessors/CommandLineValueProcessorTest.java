package com.tngtech.configbuilder.annotationprocessors;

import com.tngtech.configbuilder.annotationprocessors.CommandLineValueProcessor;
import com.tngtech.configbuilder.annotations.CommandLineValue;
import com.tngtech.configbuilder.impl.BuilderConfiguration;
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
    private BuilderConfiguration builderConfiguration;
    @Mock
    private CommandLine commandLine;
    @Mock
    private CommandLineValue commandLineValue;

    @Test
    public void testCommandLineValueProcessor(){

        CommandLineValueProcessor commandLineValueProcessor = new CommandLineValueProcessor();

        when(builderConfiguration.getCommandLineArgs()).thenReturn(commandLine);
        when(commandLineValue.shortOpt()).thenReturn("value");
        when(commandLine.getOptionValue("value")).thenReturn("passed");
        assertEquals("passed", commandLineValueProcessor.getValue(commandLineValue, builderConfiguration));
    }
}
