package com.tngtech.propertyloader;

import com.tngtech.infrastructure.io.ExtendedReader;
import com.tngtech.propertyloader.impl.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PropertyLoader {
    private static final String PROPERTY_FILE_ENCODING = "ISO-8859-1";
    private List<String> baseNames;
    private String fileExtension;
    private SuffixConfig suffixConfig;
    private OpenerConfig openerConfig;
    private PropertyLoaderFactory propertyLoaderFactory = new PropertyLoaderFactory();

    public PropertyLoader() {
    }

    public SuffixConfig getSuffixes() {
        return suffixConfig;
    }

    public void setSuffixConfig(SuffixConfig suffixConfig) {
        this.suffixConfig = suffixConfig;
    }

    public OpenerConfig getOpeners() {
        return openerConfig;
    }

    public void setOpenerConfig(OpenerConfig openerConfig) {
        this.openerConfig = openerConfig;
    }

    public String getExtension() {
        return fileExtension;
    }

    public void setExtension(String extension) {
        this.fileExtension = extension;
    }

    public void setBaseNames(List<String> baseNames) {
        this.baseNames = baseNames;
    }

    public void addBaseName(String baseName) {
        baseNames.add(baseName);
    }

    public OrderedProperties loadProperties(String baseName, String extension) {
        addBaseName(baseName);
        fileExtension = extension;
        return loadProperties();
    }

    public OrderedProperties loadProperties(String[] baseNames, String extension) {
        for(String baseName : baseNames)
        {
            addBaseName(baseName);
        }
        fileExtension = extension;
        return loadProperties();
    }

    public OrderedProperties loadProperties(){

        OrderedProperties loadedProperties = propertyLoaderFactory.getOrderedProperties();
        for (String filename : suffixConfig.getFileNames(baseNames, fileExtension))
        {
            for (PropertyLoaderOpener opener : openerConfig.getOpeners())
            {
                loadedProperties.addAll(loadOrderedPropertiesFromFile(filename, opener));
            }
        }
        return loadedProperties;
    }

    private OrderedProperties loadOrderedPropertiesFromFile(String fileName, PropertyLoaderOpener opener)
    {
        try{
            InputStream stream = opener.open(fileName);
            if(stream != null){
                OrderedPropertiesLoader orderedPropertiesLoader = propertyLoaderFactory.getOrderedPropertiesLoader();
                OrderedProperties newProperties = orderedPropertiesLoader.loadOrderedPropertiesFromStream(stream, PROPERTY_FILE_ENCODING);
                stream.close();
                return newProperties;
            }
        }
        catch(IOException e){

        }
        return propertyLoaderFactory.getOrderedProperties();
    }


}

