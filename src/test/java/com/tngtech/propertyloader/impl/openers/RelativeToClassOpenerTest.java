package com.tngtech.propertyloader.impl.openers;

import com.tngtech.propertyloader.PropertyLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RelativeToClassOpenerTest {

    @Test
    void testOpen() {

    }

    @Test
    void testThatRelativeToClassOpener_Opens_From_Root() throws IOException {
        RelativeToClassOpener relativeToClassOpener = new RelativeToClassOpener(PropertyLoader.class);
        Properties loadedProperties = new Properties();
        InputStream stream = relativeToClassOpener.open("/abc.def.properties");
        if (stream != null) {
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);
        }
        assertThat(loadedProperties).containsKey("abc");
    }

    @Test
    void testRelativeToClassOpener_Opens_From_Path_Relative_To_Class() throws IOException {
        RelativeToClassOpener relativeToClassOpener = new RelativeToClassOpener(PropertyLoader.class);
        Properties loadedProperties = new Properties();
        InputStream stream = relativeToClassOpener.open("xyz.def.properties");
        if (stream != null) {
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);
        }
        assertThat(loadedProperties).containsKey("xyz");
    }

    @Test
    void testToString() {

    }
}
