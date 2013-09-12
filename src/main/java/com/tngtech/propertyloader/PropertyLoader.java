package com.tngtech.propertyloader;

import com.tngtech.propertyloader.impl.*;
import com.tngtech.propertyloader.impl.helpers.PropertyFileNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

@Component
public class PropertyLoader {

    private final PropertyFileNameHelper propertyFileNameHelper;
    private final PropertyFileReader propertyFileReader;

    private String propertyFileEncoding = "ISO-8859-1";
    private List<String> baseNames;
    private String fileExtension = "properties";
    private PropertySuffix propertySuffix;
    private PropertyLocation propertyLocation;

    @Autowired
    public PropertyLoader(PropertyFileNameHelper propertyFileNameHelper, PropertyFileReader propertyFileReader) {
        this.propertyFileNameHelper = propertyFileNameHelper;
        this.propertyFileReader = propertyFileReader;
    }

    public PropertyLoader withEncoding(String propertyFileEncoding) {
        this.propertyFileEncoding = propertyFileEncoding;
        return this;
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

    public PropertyLoader withExtension(String extension) {
        this.fileExtension = extension;
        return this;
    }

    public PropertyLoader withBaseNames(List<String> baseNames) {
        this.baseNames = baseNames;
        return this;
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

        for (String filename : propertyFileNameHelper.getFileNames(baseNames, propertySuffix.getSuffixes(), fileExtension))
        {
            for (PropertyLoaderOpener opener : propertyLocation.getOpeners())
            {
                propertyFileReader.read(filename, propertyFileEncoding, opener);
            }
        }
        return propertyFileReader.getProperties();
    }

}

