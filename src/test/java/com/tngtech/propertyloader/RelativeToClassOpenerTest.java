package com.tngtech.propertyloader;

import com.tngtech.propertyloader.impl.openers.RelativeToClassOpener;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class RelativeToClassOpenerTest {

    @Before
    public void setUp(){
    }

    @Test
    public void testRelativeToClassOpener() throws IOException{
        RelativeToClassOpener relativeToClassOpener = new RelativeToClassOpener(PropertyLoader.class);
        Properties loadedProperties =  new Properties();
        InputStream stream = relativeToClassOpener.open("/abc.def.properties");
        if(stream != null){
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);

        }
        assertTrue(loadedProperties.containsKey("abc"));

        stream = relativeToClassOpener.open("xyz.def.properties");
        if(stream != null){
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);

        }
        assertTrue(loadedProperties.containsKey("xyz"));
    }

}
