package com.tngtech.propertyloader;

import com.tngtech.propertyloader.impl.openers.ContextClassLoaderOpener;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class ContextClassLoaderOpenerTest {

    @Before
    public void setUp(){
    }


    @Test
    public void testContextClassLoaderOpener() throws IOException{
        ContextClassLoaderOpener contextClassLoaderOpener = new ContextClassLoaderOpener();
        Properties loadedProperties =  new Properties();
        InputStream stream = contextClassLoaderOpener.open("abc.def.properties");
        if(stream != null){
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);

        }
        assertTrue(loadedProperties.containsKey("abc"));
    }

}
