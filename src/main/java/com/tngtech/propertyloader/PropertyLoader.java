package com.tngtech.propertyloader;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.context.Context;
import com.tngtech.propertyloader.exception.PropertyLoaderException;
import com.tngtech.propertyloader.impl.*;
import com.tngtech.propertyloader.impl.helpers.PropertyFileNameHelper;
import com.tngtech.propertyloader.impl.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

@Component
public class PropertyLoader implements PropertyLocations<PropertyLoader>, PropertySuffix<PropertyLoader>, PropertyFilter<PropertyLoader> {

    private static final String INCLUDE_KEY = "$include";

    private final PropertyFileNameHelper propertyFileNameHelper;
    private final PropertyFileReader propertyFileReader;
    private final PropertyLoaderFactory propertyLoaderFactory;

    private String propertyFileEncoding = "ISO-8859-1";
    private List<String> baseNames = Lists.newArrayList();
    private String fileExtension = "properties";
    private DefaultPropertySuffix propertySuffix;
    private DefaultPropertyLocation propertyLocation;
    private DefaultPropertyFilter propertyLoaderFilters;
    private Stack<String> fileNameStack;

    @Autowired
    public PropertyLoader(PropertyFileNameHelper propertyFileNameHelper, PropertyFileReader propertyFileReader, PropertyLoaderFactory propertyLoaderFactory, DefaultPropertySuffix propertySuffix, DefaultPropertyLocation propertyLocation, DefaultPropertyFilter propertyLoaderFilters) {
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
                Context.getBean(DefaultPropertySuffix.class),
                Context.getBean(DefaultPropertyLocation.class),
                Context.getBean(DefaultPropertyFilter.class));
    }

    public PropertyLoader withEncoding(String propertyFileEncoding) {
        this.propertyFileEncoding = propertyFileEncoding;
        return this;
    }

    public DefaultPropertyLocation getLocations() {
        return propertyLocation;
    }

    public DefaultPropertySuffix getSuffixes() {
        return propertySuffix;
    }
    public DefaultPropertyFilter getFilters() {
        return propertyLoaderFilters;
    }

    public String getExtension() {
        return fileExtension;
    }

    public void withSuffixes(DefaultPropertySuffix propertySuffix) {
        this.propertySuffix = propertySuffix;
    }

    public void withLocations(DefaultPropertyLocation propertyLocation) {
        this.propertyLocation = propertyLocation;
    }

    public void withFilters(DefaultPropertyFilter propertyFilter) {
        this.propertyLoaderFilters = propertyFilter;
    }

    public PropertyLoader withExtension(String extension) {
        this.fileExtension = extension;
        return this;
    }

    public PropertyLoader withBaseNames(List<String> baseNames) {
        this.baseNames = baseNames;
        return this;
    }

    public PropertyLoader withDefaultConfig() {
        this.propertyLocation.clear().atDefaultLocations();
        this.propertySuffix.clear().addDefaultSuffixes();
        this.propertyLoaderFilters.clear().withDefaultFilters();
        return this;
    }

    public Properties load(String baseName) {
        fileNameStack = new Stack<>();

        Properties loadedProperties = loadPropertiesFromBaseNameList(Lists.newArrayList(baseName));
        filterProperties(loadedProperties);
        return loadedProperties;
    }

    public Properties load(String[] baseNames) {
        fileNameStack = new Stack<>();

        Properties loadedProperties = loadPropertiesFromBaseNameList(Lists.newArrayList(baseNames));
        filterProperties(loadedProperties);
        return loadedProperties;
    }

    public Properties load() {
        fileNameStack = new Stack<>();

        Properties loadedProperties = loadPropertiesFromBaseNameList(this.baseNames);
        filterProperties(loadedProperties);
        return loadedProperties;
    }

    private Properties loadPropertiesFromBaseNameList(List<String> baseNames) {
        Properties loadedProperties = propertyLoaderFactory.getEmptyProperties();
        for (String fileName : propertyFileNameHelper.getFileNames(baseNames, propertySuffix.getSuffixes(), fileExtension))
        {
            if (fileNameStack.contains(fileName)) {
                StringBuilder sb = new StringBuilder();
                sb.append("property file include recursion: ");
                Enumeration<String> elements = fileNameStack.elements();
                while (elements.hasMoreElements()) {
                    String currentFileName = elements.nextElement();
                    sb.append(currentFileName);
                    sb.append(" -> ");
                }
                sb.append(fileName);
                throw new PropertyLoaderException(sb.toString());
            }

            fileNameStack.push(fileName);
            for (PropertyLoaderOpener opener : propertyLocation.getOpeners())
            {
                Properties newProperties = propertyFileReader.tryToReadPropertiesFromFile(fileName, propertyFileEncoding, opener);
                Properties includedProperties = loadPropertiesFromBaseNameList(Lists.newArrayList(collectIncludesAndRemoveKey(newProperties)));
                newProperties.putAll(includedProperties);
                loadedProperties.putAll(newProperties);
            }
            fileNameStack.pop();
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

    public PropertyLoader atDefaultLocations(){
        propertyLocation.atDefaultLocations();
        return this;
    }

    public PropertyLoader atCurrentDirectory(){
        propertyLocation.atCurrentDirectory();
        return this;
    }

    public PropertyLoader atHomeDirectory(){
        propertyLocation.atHomeDirectory();
        return this;
    }

    public PropertyLoader atDirectory(String directory){
        propertyLocation.atDirectory(directory);
        return this;
    }


    public PropertyLoader atContextClassPath(){
        propertyLocation.atContextClassPath();
        return this;
    }

    public PropertyLoader atRelativeToClass(Class<?> reference){
        propertyLocation.atRelativeToClass(reference);
        return this;
    }

    public PropertyLoader fromClassLoader(ClassLoader classLoader){
        propertyLocation.fromClassLoader(classLoader);
        return this;
    }

    public PropertyLoader atBaseURL(URL url){
        propertyLocation.atBaseURL(url);
        return this;
    }

    public PropertyLoader addUserName()
    {
        propertySuffix.addUserName();
        return this;
    }

    public PropertyLoader addLocalHostNames()
    {
        propertySuffix.addLocalHostNames();
        return this;
    }

    public PropertyLoader addString(String suffix)
    {
        propertySuffix.addString(suffix);
        return this;
    }

    public PropertyLoader addSuffixList(List<String> suffixes) {
        propertySuffix.addSuffixList(suffixes);
        return this;
    }

    public PropertyLoader addDefaultSuffixes() {
        propertySuffix.addDefaultSuffixes();
        return this;
    }

    public PropertyLoader withDefaultFilters() {
        propertyLoaderFilters.withDefaultFilters();
        return this;
    }

    public PropertyLoader withVariableResolvingFilter() {
        propertyLoaderFilters.withVariableResolvingFilter();
        return this;
    }

    public PropertyLoader withEnvironmentResolvingFilter() {
        propertyLoaderFilters.withEnvironmentResolvingFilter();
        return this;
    }

    public PropertyLoader withWarnIfPropertyHasToBeDefined() {
        propertyLoaderFilters.withWarnIfPropertyHasToBeDefined();
        return this;
    }

    public PropertyLoader withWarnOnSurroundingWhitespace() {
        propertyLoaderFilters.withWarnOnSurroundingWhitespace();
        return this;
    }
}

