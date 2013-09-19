package com.tngtech.propertyloader;


import com.tngtech.propertyloader.impl.openers.ClassLoaderOpener;
import com.tngtech.propertyloader.impl.openers.ContextClassLoaderOpener;
import com.tngtech.propertyloader.impl.openers.RelativeToClassOpener;
import com.tngtech.propertyloader.impl.openers.URLFileOpener;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class PropertyLoaderOpenerTest {

    @Before
    public void setUp(){
    }

    @Test
    public void testThatURLFileOpenerLoadsFromURl() throws IOException{
        URL url = new File("/home/matthias/Projects/property-loader-rewrite/src/test/resources/").toURI().toURL();
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
        String path = "/home/matthias/Projects/property-loader-rewrite/src/test/resources/";
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
        URL[] urls = {new File("/home/matthias/Projects/property-loader-rewrite/src/test/resources/sefshgejkf").toURI().toURL()};
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
