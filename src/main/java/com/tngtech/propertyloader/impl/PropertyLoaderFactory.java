package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.openers.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

@Component
public class PropertyLoaderFactory {

    public Properties getEmptyProperties(){
        return new Properties();
    }

    public InputStreamReader getInputStreamReader(InputStream stream, String encoding) throws IOException {
        return new InputStreamReader(stream, encoding);
    }

    public FilesystemOpener getFilesystemOpener(){
        return new FilesystemOpener();
    }

    public FilesystemOpener getFilesystemOpener(String directory){
        return new FilesystemOpener(directory);
    }

    public ContextClassLoaderOpener getContextClassLoaderOpener(){
        return new ContextClassLoaderOpener();
    }

    public RelativeToClass getRelativeToClass(Class<?> tClass){
        return new RelativeToClass(tClass);
    }

    public ClassLoaderOpener getClassLoaderOpener(ClassLoader classLoader){
        return new ClassLoaderOpener(classLoader);
    }

    public WebOpener getWebOpener(URL url){
        return new WebOpener(url);
    }

}
