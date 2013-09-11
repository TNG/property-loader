package com.tngtech.propertyloader;

import com.tngtech.propertyloader.impl.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

public class PropertyLoader {
    private String propertyFileEncoding = "ISO-8859-1";
    private List<String> baseNames;
    private String fileExtension = "properties";
    private PropertySuffix propertySuffix;
    private PropertyLocation propertyLocation;
    private PropertyLoaderFactory propertyLoaderFactory;

    public PropertyLoader() {
    }

    public void withEncoding(String propertyFileEncoding) {
        this.propertyFileEncoding = propertyFileEncoding;
    }

    public PropertySuffix getSuffixes() {
        return propertySuffix;
    }

    public void searchSuffixes(PropertySuffix propertySuffix) {
        this.propertySuffix = propertySuffix;
    }

    public PropertyLocation getOpeners() {
        return propertyLocation;
    }

    public void searchLocations(PropertyLocation propertyLocation) {
        this.propertyLocation = propertyLocation;
    }

    public String getExtension() {
        return fileExtension;
    }

    public void withExtension(String extension) {
        this.fileExtension = extension;
    }

    public void withBaseNames(List<String> baseNames) {
        this.baseNames = baseNames;
    }

    public void addBaseName(String baseName) {
        baseNames.add(baseName);
    }

    public Properties loadProperties(String baseName, String extension) {
        addBaseName(baseName);
        fileExtension = extension;
        return loadProperties();
    }

    public Properties loadProperties(String[] baseNames, String extension) {
        for(String baseName : baseNames)
        {
            addBaseName(baseName);
        }
        fileExtension = extension;
        return loadProperties();
    }

    public Properties loadProperties(){

        Properties loadedProperties = propertyLoaderFactory.getEmptyProperties();
        for (String filename : propertySuffix.getFileNames(baseNames, fileExtension))
        {
            for (PropertyLoaderOpener opener : propertyLocation.getOpeners())
            {
                loadPropertiesFromFile(filename, opener, loadedProperties);

            }
        }
        return loadedProperties;
    }

    private void loadPropertiesFromFile(String fileName, PropertyLoaderOpener opener, Properties loadedProperties)
    {
        try{
            InputStream stream = opener.open(fileName);
            if(stream != null){
                Reader reader = propertyLoaderFactory.getInputStreamReader(stream, propertyFileEncoding);
                loadedProperties.load(reader);
            }
        }
        catch(IOException e){

        }
    }

    public void setPropertyLoaderFactory(PropertyLoaderFactory propertyLoaderFactory) {
        this.propertyLoaderFactory = propertyLoaderFactory;
    }
}

