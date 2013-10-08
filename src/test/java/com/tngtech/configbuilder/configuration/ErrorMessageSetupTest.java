package com.tngtech.configbuilder.configuration;

import com.tngtech.propertyloader.PropertyLoader;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Locale;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ErrorMessageSetupTest {

    @Mock
    private PropertyLoader propertyLoader;

    private ErrorMessageSetup errorMessageSetup;

    @Before
    public void setUp() throws Exception {
        errorMessageSetup = new ErrorMessageSetup();
        when(propertyLoader.withExtension("properties")).thenReturn(propertyLoader);
        when(propertyLoader.load("errors")).thenReturn(new Properties());
    }

    @Test
    public void testInitializeDE() throws Exception {
        Locale.setDefault(Locale.GERMAN);
        errorMessageSetup.initialize("errors", propertyLoader);
        assertEquals("Command Line Argumente konnten nicht verarbeitet werden.", errorMessageSetup.getErrorMessage(ParseException.class));
    }

    @Test
    public void testInitializeEN() throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        errorMessageSetup.initialize("errors", propertyLoader);
        assertEquals("unable to parse command line arguments",errorMessageSetup.getErrorMessage(ParseException.class));
    }

    @Test
    public void testInitializeOther() throws Exception {
        Locale.setDefault(Locale.ITALIAN);
        errorMessageSetup.initialize("errors", propertyLoader);
        assertEquals("unable to parse command line arguments",errorMessageSetup.getErrorMessage(ParseException.class));
    }

    @Test
    public void testGetErrorMessageForExceptionInstance() throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        errorMessageSetup.initialize(null, propertyLoader);
        ParseException parseException = new ParseException("message");
        assertEquals("unable to parse command line arguments",errorMessageSetup.getErrorMessage(parseException));
    }

    @Test
    public void testGetErrorMessageForUnknownException() throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        errorMessageSetup.initialize(null, propertyLoader);
        RuntimeException runtimeException = new RuntimeException();
        assertEquals("java.lang.RuntimeException was thrown",errorMessageSetup.getErrorMessage(runtimeException));
        assertEquals("java.lang.RuntimeException was thrown",errorMessageSetup.getErrorMessage(RuntimeException.class));
    }
}
