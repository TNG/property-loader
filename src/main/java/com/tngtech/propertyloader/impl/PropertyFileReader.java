package com.tngtech.propertyloader.impl;


import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

@Component
public class PropertyFileReader {

    private PropertyLoaderFactory propertyLoaderFactory = new PropertyLoaderFactory();
    private Properties loadedProperties = propertyLoaderFactory.getEmptyProperties();

    public Properties getProperties(){
        return loadedProperties;
    }

    public void read(String fileName, String encoding, PropertyLoaderOpener opener)
    {
        try{
            InputStream stream = opener.open(fileName);
            if(stream != null){
                Reader reader = propertyLoaderFactory.getInputStreamReader(stream, encoding);
                loadedProperties.load(reader);
            }
        }
        catch(IOException e){

        }
    }
}
