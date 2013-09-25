package com.tngtech.propertyloader;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.context.Context;
import com.tngtech.propertyloader.impl.*;
import com.tngtech.propertyloader.impl.helpers.PropertyFileNameHelper;
import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderFilter;
import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderOpener;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
public class PropertyLoader {

    private static final String INCLUDE_KEY = "$include";

    private final PropertyFileNameHelper propertyFileNameHelper;
    private final PropertyFileReader propertyFileReader;
    private final PropertyLoaderFactory propertyLoaderFactory;

    private String propertyFileEncoding = "ISO-8859-1";
    private List<String> baseNames = Lists.newArrayList();
    private String fileExtension = "properties";
    private PropertySuffix propertySuffix;
    private PropertyLocation propertyLocation;
    private PropertyFilter  propertyLoaderFilters;

    @Autowired
    public PropertyLoader(PropertyFileNameHelper propertyFileNameHelper, PropertyFileReader propertyFileReader, PropertyLoaderFactory propertyLoaderFactory, PropertySuffix propertySuffix, PropertyLocation propertyLocation, PropertyFilter propertyLoaderFilters) {
        this.propertyFileNameHelper = propertyFileNameHelper;
        this.propertyFileReader = propertyFileReader;
        this.propertyLoaderFactory = propertyLoaderFactory;
        this.propertySuffix = propertySuffix;
        this.propertyLocation = propertyLocation;
        this.propertyLoaderFilters = propertyLoaderFilters;
    }

    public PropertyLoader(){
        this(Context.getBean(PropertyFileNameHelper.class),
                Context.getBean(PropertyFileReader.class),
                Context.getBean(PropertyLoaderFactory.class),
                Context.getBean(PropertySuffix.class),
                Context.getBean(PropertyLocation.class),
                Context.getBean(PropertyFilter.class));
    }

    public PropertyLoader withEncoding(String propertyFileEncoding) {
        this.propertyFileEncoding = propertyFileEncoding;
        return this;
    }

    public PropertyLocation getLocations() {
        return propertyLocation;
    }

    public PropertySuffix getSuffixes() {
        return propertySuffix;
    }

    public void withSuffixes(PropertySuffix propertySuffix) {
        this.propertySuffix = propertySuffix;
    }

    public void withLocations(PropertyLocation propertyLocation) {
        this.propertyLocation = propertyLocation;
    }

    public void withFilters(PropertyFilter propertyFilter) {
        this.propertyLoaderFilters = propertyFilter;
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

    public PropertyLoader addBaseNames(List<String> baseNames) {
        this.baseNames.addAll(baseNames);
        return this;
    }

    public PropertyLoader withDefaultConfig() {
        this.propertyLocation.clear().atDefaultLocations();
        this.propertySuffix.clear().addDefaultConfig();
        this.propertyLoaderFilters.clear().withDefaultFilters();
        return this;
    }

    public Properties load(String baseName) {
        Properties loadedProperties = loadPropertiesFromBaseNameList(Lists.newArrayList(baseName));
        filterProperties(loadedProperties);
        return loadedProperties;
    }

    public Properties load(String[] baseNames) {
        Properties loadedProperties = loadPropertiesFromBaseNameList(Lists.newArrayList(baseNames));
        filterProperties(loadedProperties);
        return loadedProperties;
    }

    public Properties load(String baseName, String extension) {
        this.fileExtension = extension;
        Properties loadedProperties = loadPropertiesFromBaseNameList(Lists.newArrayList(baseName));
        filterProperties(loadedProperties);
        return loadedProperties;
    }

    public Properties load(String[] baseNames, String extension) {
        this.fileExtension = extension;
        Properties loadedProperties = loadPropertiesFromBaseNameList(Lists.newArrayList(baseNames));
        filterProperties(loadedProperties);
        return loadedProperties;
    }

    public Properties load(){

        Properties loadedProperties = loadPropertiesFromBaseNameList(this.baseNames);
        filterProperties(loadedProperties);
        return loadedProperties;
    }

    private Properties loadPropertiesFromBaseNameList(List<String> baseNames) {
        Properties loadedProperties = propertyLoaderFactory.getEmptyProperties();
        for (String fileName : propertyFileNameHelper.getFileNames(baseNames, propertySuffix.getSuffixes(), fileExtension))
        {
            for (PropertyLoaderOpener opener : propertyLocation.getOpeners())
            {
                Properties newProperties = propertyFileReader.tryToReadPropertiesFromFile(fileName, propertyFileEncoding, opener);
                Properties includedProperties = loadPropertiesFromBaseNameList(Lists.newArrayList(collectIncludesAndRemoveKey(newProperties)));
                newProperties.putAll(includedProperties);
                loadedProperties.putAll(newProperties);
            }
        }
        return loadedProperties;
    }

    private String[] collectIncludesAndRemoveKey(Properties properties) {
        String[] includes = new String[]{};
        if(properties.containsKey(INCLUDE_KEY)) {
            includes = properties.getProperty(INCLUDE_KEY).split(",");
            properties.remove(INCLUDE_KEY);
        }
        return includes;
    }

    private void filterProperties(Properties loadedProperties) {
        for(PropertyLoaderFilter filter : propertyLoaderFilters.getFilters()) {
            filter.filter(loadedProperties);
        }
    }
}

