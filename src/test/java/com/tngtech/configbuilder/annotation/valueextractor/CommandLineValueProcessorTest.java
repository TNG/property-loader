package com.tngtech.configbuilder.annotation.valueextractor;

import com.tngtech.configbuilder.configuration.BuilderConfiguration;
import org.apache.commons.cli.CommandLine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineValueProcessorTest {

    private CommandLineValueProcessor commandLineValueProcessor;

    @Mock
    private BuilderConfiguration builderConfiguration;
    @Mock
    private CommandLine commandLine;
    @Mock
    private CommandLineValue commandLineValue;

    @Before
    public void setUp() throws Exception {
        commandLineValueProcessor = new CommandLineValueProcessor();
    }

    @Test
    public void testCommandLineValueProcessor(){

        when(builderConfiguration.getCommandLine()).thenReturn(commandLine);
        when(commandLineValue.shortOpt()).thenReturn("value");
        when(commandLine.getOptionValue("value")).thenReturn("passed");
        assertEquals("passed", commandLineValueProcessor.getValue(commandLineValue, builderConfiguration));
    }
}
