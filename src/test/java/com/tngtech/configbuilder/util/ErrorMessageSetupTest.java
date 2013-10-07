package com.tngtech.configbuilder.util;

import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.propertyloader.PropertyLoader;
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
        Properties propertiesDE = new Properties();
        propertiesDE.put("commandLineException","Command Line Argumente konnten nicht verarbeitet werden.");
        Properties propertiesEN = new Properties();
        propertiesEN.put("commandLineException","unable to parse command line arguments");

        when(propertyLoader.withExtension("properties")).thenReturn(propertyLoader);
        when(propertyLoader.load("errors")).thenReturn(new Properties());
        when(propertyLoader.load("errors_is")).thenReturn(new Properties());
        when(propertyLoader.load("errors_de")).thenReturn(propertiesDE);
        when(propertyLoader.load("errors_en")).thenReturn(propertiesEN);

    }

    @Test
    public void testInitializeDE() throws Exception {
        System.setProperty("user.language", "de");
        System.setProperty("user.country", "DE");
        errorMessageSetup.initialize("errors", propertyLoader);
        assertEquals("Command Line Argumente konnten nicht verarbeitet werden.",errorMessageSetup.getErrorMessage("commandLineException"));
    }

    @Test
    public void testInitializeEN() throws Exception {
        System.setProperty("user.language", "en");
        System.setProperty("user.country", "US");
        errorMessageSetup.initialize("errors", propertyLoader);
        assertEquals("unable to parse command line arguments",errorMessageSetup.getErrorMessage("commandLineException"));
    }

    @Test
    @Ignore("depends on locale")
    public void testInitializeOther() throws Exception {
        System.setProperty("user.language", "is");
        System.setProperty("user.country", "IS");
        errorMessageSetup.initialize("errors", propertyLoader);
        assertEquals("unable to parse command line arguments",errorMessageSetup.getErrorMessage("commandLineException"));
    }

    @Test
    public void testGetString() throws Exception {

    }
}
