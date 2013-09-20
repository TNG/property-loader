package com.tngtech.propertyloader.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

@Component
public class PropertyFileReader {

    private final PropertyLoaderFactory propertyLoaderFactory;

    @Autowired
    public PropertyFileReader(PropertyLoaderFactory propertyLoaderFactory) {
        this.propertyLoaderFactory = propertyLoaderFactory;
    }


    public Properties read(String fileName, String encoding, PropertyLoaderOpener opener)
    {
        Properties loadedProperties =  propertyLoaderFactory.getEmptyProperties();
        try{
            InputStream stream = opener.open(fileName);
            if(stream != null){
                Reader reader = propertyLoaderFactory.getInputStreamReader(stream, encoding);
                loadedProperties.load(reader);
            }
        }
        catch(IOException e){
            throw new PropertyFileReaderException(String.format("error reading properties from stream created from '%s' with encoding '%s' in opener '%s'", fileName, encoding, opener.toString()), e);
        }
        return loadedProperties;
    }
}
