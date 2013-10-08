package com.tngtech.configbuilder.configuration;

import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.propertyloader.PropertyLoader;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
        System.setProperty("user.language", "de");
        System.setProperty("user.country", "DE");
        errorMessageSetup.initialize("errors", propertyLoader);
        assertEquals("Command Line Argumente konnten nicht verarbeitet werden.", errorMessageSetup.getErrorMessage(ParseException.class));
    }

    @Test
    public void testInitializeEN() throws Exception {
        System.setProperty("user.language", "en");
        System.setProperty("user.country", "US");
        errorMessageSetup.initialize("errors", propertyLoader);
        assertEquals("unable to parse command line arguments",errorMessageSetup.getErrorMessage(ParseException.class));
    }

    @Test
    @Ignore("depends on locale")
    public void testInitializeOther() throws Exception {
        System.setProperty("user.language", "is");
        System.setProperty("user.country", "IS");
        errorMessageSetup.initialize("errors", propertyLoader);
        assertEquals("unable to parse command line arguments",errorMessageSetup.getErrorMessage(ParseException.class));
    }

    @Test
    public void testGetErrorMessageForExceptionInstance() throws Exception {
        errorMessageSetup.initialize(null, propertyLoader);
        ParseException parseException = new ParseException("message");
        assertEquals("Command Line Argumente konnten nicht verarbeitet werden.",errorMessageSetup.getErrorMessage(parseException));
    }

    @Test
    public void testGetErrorMessageForUnknownException() throws Exception {
        errorMessageSetup.initialize(null, propertyLoader);
        RuntimeException runtimeException = new RuntimeException();
        assertEquals("Es gab eine Exception vom Typ java.lang.RuntimeException",errorMessageSetup.getErrorMessage(runtimeException));
        assertEquals("Es gab eine Exception vom Typ java.lang.RuntimeException",errorMessageSetup.getErrorMessage(RuntimeException.class));
    }
}
