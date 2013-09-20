package com.tngtech.propertyloader.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

@Component
public class PropertyFileReader {

    private final static Logger log = Logger.getLogger(PropertyFileReader.class);

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
            else{
                log.info(String.format("file %s not found %s", fileName, opener.toString()));
            }
        }
        catch(IOException e){
            throw new PropertyFileReaderException(String.format("error reading properties from stream created from '%s' with encoding '%s' in opener '%s'", fileName, encoding, opener.toString()), e);
        }
        return loadedProperties;
    }
}
