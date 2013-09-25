package com.tngtech.propertyloader.impl.openers;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class URLFileOpenerTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testThatURLFileOpenerLoadsFromURl() throws IOException {

        URL urls = this.getClass().getResource("/abc.def.properties");
        URL url = new File(urls.getPath().replace("abc.def.properties", "")).toURI().toURL();
        URLFileOpener urlFileOpener = new URLFileOpener(url);
        Properties loadedProperties =  new Properties();
        InputStream stream = urlFileOpener.open("abc.def.properties");
        if(stream != null){
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);

        }
        assertTrue(loadedProperties.containsKey("abc"));
    }

    @Test
    public void testThatURLFileOpenerLoadsFromPathString() throws IOException{
        URL urls = this.getClass().getResource("/abc.def.properties");
        String path = urls.getPath().replace("abc.def.properties", "");
        URLFileOpener urlFileOpener = new URLFileOpener(path);
        Properties loadedProperties =  new Properties();
        InputStream stream = urlFileOpener.open("abc.def.properties");
        if(stream != null){
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);

        }
        assertTrue(loadedProperties.containsKey("abc"));
    }

    @Test
    public void testThatURLFileOpener_Forgets_Path_When_Provided_With_FileName_That_Has_Preceding_Slash() throws IOException{
        URL urls = this.getClass().getResource("/abc.def.properties");
        String path = urls.getPath().replace("abc.def.properties", "");
        URLFileOpener urlFileOpener = new URLFileOpener(path);
        Properties loadedProperties =  new Properties();
        InputStream stream = urlFileOpener.open(path + "abc.def.properties");
        if(stream != null){
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);

        }
        assertTrue(loadedProperties.containsKey("abc"));
    }

    @Test
    public void testThat_Path_Does_Not_Change_When_URLFileOpener_Opens_Files() throws IOException{
        URL urls = this.getClass().getResource("/abc.def.properties");
        String path = urls.getPath().replace("abc.def.properties", "");
        URLFileOpener urlFileOpener = new URLFileOpener(path);
        urlFileOpener.open(path + "abc.def.properties");
        assertTrue(urlFileOpener.toString().replace("in path ","").equals(path));
    }

    @Test
    public void testToString() throws Exception {

    }
}
