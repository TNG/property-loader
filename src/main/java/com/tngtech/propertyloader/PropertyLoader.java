package com.tngtech.propertyloader;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.context.Context;
import com.tngtech.propertyloader.impl.*;
import com.tngtech.propertyloader.impl.helpers.PropertyFileNameHelper;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
public class PropertyLoader {

    private final static Logger log = Logger.getLogger(PropertyLoader.class);

    private final PropertyFileNameHelper propertyFileNameHelper;
    private final PropertyFileReader propertyFileReader;
    private final PropertyLoaderFactory propertyLoaderFactory;

    private String propertyFileEncoding = "ISO-8859-1";
    private List<String> baseNames = Lists.newArrayList();
    private String fileExtension = "properties";
    private PropertySuffix propertySuffix;
    private PropertyLocation propertyLocation;

    @Autowired
    public PropertyLoader(PropertyFileNameHelper propertyFileNameHelper, PropertyFileReader propertyFileReader, PropertyLoaderFactory propertyLoaderFactory, PropertySuffix propertySuffix, PropertyLocation propertyLocation) {
        this.propertyFileNameHelper = propertyFileNameHelper;
        this.propertyFileReader = propertyFileReader;
        this.propertyLoaderFactory = propertyLoaderFactory;
        this.propertySuffix = propertySuffix;
        this.propertyLocation = propertyLocation;
    }

    public PropertyLoader(){
        this(Context.getBean(PropertyFileNameHelper.class),
                Context.getBean(PropertyFileReader.class),
                Context.getBean(PropertyLoaderFactory.class),
                Context.getBean(PropertySuffix.class),
                Context.getBean(PropertyLocation.class));
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

    public PropertyLocation getLocations() {
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

    public PropertyLoader withDefaultConfig(List<String> baseNames) {
        this.propertyLocation = Context.getBean(PropertyLocation.class).atDefaultLocations();
        this.propertySuffix = Context.getBean(PropertySuffix.class).addDefaultConfig();
        return this;
    }

    public Properties loadProperties(String baseName) {
        this.baseNames.add(baseName);
        return loadProperties();
    }

    public Properties loadProperties(String[] baseNames) {
        for(String baseName : baseNames)
        {
            this.baseNames.add(baseName);
        }
        return loadProperties();
    }

    public Properties loadProperties(String baseName, String extension) {
        this.baseNames.add(baseName);
        fileExtension = extension;
        return loadProperties();
    }

    public Properties loadProperties(String[] baseNames, String extension) {
        for(String baseName : baseNames)
        {
            this.baseNames.add(baseName);
        }
        fileExtension = extension;
        return loadProperties();
    }

    public Properties loadProperties(){

        Properties loadedProperties = propertyLoaderFactory.getEmptyProperties();
        for (String fileName : propertyFileNameHelper.getFileNames(baseNames, propertySuffix.getSuffixes(), fileExtension))
        {
            for (PropertyLoaderOpener opener : propertyLocation.getOpeners())
            {
                log.debug(String.format("attempting to read file %s with encoding %s in %s", fileName, propertyFileEncoding, opener.toString()));
                Properties newProperties = propertyFileReader.read(fileName, propertyFileEncoding, opener);
                loadedProperties.putAll(newProperties);
            }
        }
        return loadedProperties;
    }
}

