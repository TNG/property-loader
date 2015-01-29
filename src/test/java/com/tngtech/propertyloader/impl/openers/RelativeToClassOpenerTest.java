package com.tngtech.propertyloader.impl.openers;

import com.tngtech.propertyloader.PropertyLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class RelativeToClassOpenerTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testOpen() {

    }

    @Test
    public void testThatRelativeToClassOpener_Opens_From_Root() throws IOException {
        RelativeToClassOpener relativeToClassOpener = new RelativeToClassOpener(PropertyLoader.class);
        Properties loadedProperties = new Properties();
        InputStream stream = relativeToClassOpener.open("/abc.def.properties");
        if (stream != null) {
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);
        }
        assertTrue(loadedProperties.containsKey("abc"));
    }

    @Test
    public void testRelativeToClassOpener_Opens_From_Path_Relative_To_Class() throws IOException {
        RelativeToClassOpener relativeToClassOpener = new RelativeToClassOpener(PropertyLoader.class);
        Properties loadedProperties = new Properties();
        InputStream stream = relativeToClassOpener.open("xyz.def.properties");
        if (stream != null) {
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);
        }
        assertTrue(loadedProperties.containsKey("xyz"));
    }

    @Test
    public void testToString() {

    }
}
