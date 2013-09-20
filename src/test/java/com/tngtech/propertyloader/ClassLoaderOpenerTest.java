package com.tngtech.propertyloader;


import com.tngtech.propertyloader.impl.openers.ClassLoaderOpener;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class ClassLoaderOpenerTest {

    @Before
    public void setUp(){
    }


    @Test
    public void testClassLoaderOpener() throws IOException{
        ClassLoaderOpener classLoaderOpener = new ClassLoaderOpener(PropertyLoader.class.getClassLoader());
        Properties loadedProperties =  new Properties();
        InputStream stream = classLoaderOpener.open("abc.def.properties");
        if(stream != null){
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);

        }
        assertTrue(loadedProperties.containsKey("abc"));
    }

    @Test
    public void testClassLoaderOpenerForURLClassLoader() throws IOException{
        URL[] urls = {new File("").toURI().toURL()};
        ClassLoader classLoader = new URLClassLoader(urls);
        ClassLoaderOpener classLoaderOpener = new ClassLoaderOpener(classLoader);
        Properties loadedProperties =  new Properties();
        InputStream stream = classLoaderOpener.open("abc.def.properties");
        if(stream != null){
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);

        }
        assertTrue(loadedProperties.containsKey("abc"));
    }


}
