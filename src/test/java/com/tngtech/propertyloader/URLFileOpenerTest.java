package com.tngtech.propertyloader;

import com.tngtech.propertyloader.impl.openers.URLFileOpener;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class URLFileOpenerTest {

    @Before
    public void setUp(){
    }

    @Test
    public void testThatURLFileOpenerLoadsFromURl() throws IOException{

        URL urls = this.getClass().getResource("/abc.def.properties");
        URL url = new File(urls.getPath()).toURI().toURL();
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
        String path = urls.getPath();
        URLFileOpener urlFileOpener = new URLFileOpener(path);
        Properties loadedProperties =  new Properties();
        InputStream stream = urlFileOpener.open("abc.def.properties");
        if(stream != null){
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);

        }
        assertTrue(loadedProperties.containsKey("abc"));
    }

}
