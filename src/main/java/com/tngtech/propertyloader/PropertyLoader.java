package com.tngtech.propertyloader;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.exception.PropertyLoaderException;
import com.tngtech.propertyloader.impl.*;
import com.tngtech.propertyloader.impl.helpers.HostsHelper;
import com.tngtech.propertyloader.impl.helpers.PropertyFileNameHelper;
import com.tngtech.propertyloader.impl.interfaces.*;

import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

/**
 * Implements loading of java.util.Properties from properties-like or XML key-value files.
 * <p/>
 * To obtain an instance with default configuration, use <code>new PropertyLoader().withDefaultConfig()</code>.
 * <p/>
 * To see which search paths are currently implemented, see the impl.openers subpackage.
 * To see which postprocessing steps are implemented, see the impl.filters subpackage.
 */
public class PropertyLoader implements PropertyLocationsContainer<PropertyLoader>, PropertySuffixContainer<PropertyLoader>, PropertyFilterContainer<PropertyLoader> {

    private static final String INCLUDE_KEY = "$include";

    private final PropertyFileNameHelper propertyFileNameHelper;
    private final PropertyFileReader propertyFileReader;
    private final PropertyLoaderFactory propertyLoaderFactory;

    private String propertyFileEncoding = "ISO-8859-1";
    private List<String> baseNames = Lists.newArrayList();
    private String fileExtension = "properties";
    private DefaultPropertySuffixContainer propertySuffix;
    private DefaultPropertyLocationContainer propertyLocation;
    private DefaultPropertyFilterContainer propertyLoaderFilters;
    private Stack<String> fileNameStack;

    protected PropertyLoader(PropertyFileNameHelper propertyFileNameHelper, PropertyFileReader propertyFileReader, PropertyLoaderFactory propertyLoaderFactory, DefaultPropertySuffixContainer propertySuffix, DefaultPropertyLocationContainer propertyLocation, DefaultPropertyFilterContainer propertyLoaderFilters) {
        this.propertyFileNameHelper = propertyFileNameHelper;
        this.propertyFileReader = propertyFileReader;
        this.propertyLoaderFactory = propertyLoaderFactory;
        this.propertySuffix = propertySuffix;
        this.propertyLocation = propertyLocation;
        this.propertyLoaderFilters = propertyLoaderFilters;
    }

    public PropertyLoader() {

        PropertyLoaderFactory propertyLoaderFactory = new PropertyLoaderFactory();
        HostsHelper hostsHelper = propertyLoaderFactory.getBean(HostsHelper.class);

        this.propertyFileNameHelper = propertyLoaderFactory.getBean(PropertyFileNameHelper.class);
        this.propertyFileReader = new PropertyFileReader(propertyLoaderFactory);
        this.propertyLoaderFactory = propertyLoaderFactory;
        this.propertySuffix = new DefaultPropertySuffixContainer(hostsHelper);
        this.propertyLocation = new DefaultPropertyLocationContainer(propertyLoaderFactory);
        this.propertyLoaderFilters = new DefaultPropertyFilterContainer(propertyLoaderFactory);
    }

    public PropertyLoader withEncoding(String propertyFileEncoding) {
        this.propertyFileEncoding = propertyFileEncoding;
        return this;
    }

    public DefaultPropertyLocationContainer getLocations() {
        return propertyLocation;
    }

    public DefaultPropertySuffixContainer getSuffixes() {
        return propertySuffix;
    }

    public DefaultPropertyFilterContainer getFilters() {
        return propertyLoaderFilters;
    }

    public String getExtension() {
        return fileExtension;
    }

    public void withSuffixes(DefaultPropertySuffixContainer propertySuffix) {
        this.propertySuffix = propertySuffix;
    }

    public void withLocations(DefaultPropertyLocationContainer propertyLocation) {
        this.propertyLocation = propertyLocation;
    }

    public void withFilters(DefaultPropertyFilterContainer propertyFilter) {
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


    public PropertyLoader atDefaultLocations() {
        propertyLocation.atDefaultLocations();
        return this;
    }

    public PropertyLoader atCurrentDirectory() {
        propertyLocation.atCurrentDirectory();
        return this;
    }

    public PropertyLoader atHomeDirectory() {
        propertyLocation.atHomeDirectory();
        return this;
    }

    public PropertyLoader atDirectory(String directory) {
        propertyLocation.atDirectory(directory);
        return this;
    }


    public PropertyLoader atContextClassPath() {
        propertyLocation.atContextClassPath();
        return this;
    }

    public PropertyLoader atRelativeToClass(Class<?> reference) {
        propertyLocation.atRelativeToClass(reference);
        return this;
    }

    public PropertyLoader atClassLoader(ClassLoader classLoader) {
        propertyLocation.atClassLoader(classLoader);
        return this;
    }

    public PropertyLoader atBaseURL(URL url) {
        propertyLocation.atBaseURL(url);
        return this;
    }

    public PropertyLoader addUserName() {
        propertySuffix.addUserName();
        return this;
    }

    public PropertyLoader addLocalHostNames() {
        propertySuffix.addLocalHostNames();
        return this;
    }

    public PropertyLoader addString(String suffix) {
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

    public PropertyLoader withDecryptingFilter() {
        propertyLoaderFilters.withDecryptingFilter();
        return this;
    }

    public Properties load(String baseName) {
        fileNameStack = propertyLoaderFactory.getEmptyFileNameStack();

        Properties loadedProperties = loadPropertiesFromBaseNameList(Lists.newArrayList(baseName));
        filterProperties(loadedProperties);
        return loadedProperties;
    }

    public Properties load(String[] baseNames) {
        fileNameStack = propertyLoaderFactory.getEmptyFileNameStack();

        Properties loadedProperties = loadPropertiesFromBaseNameList(Lists.newArrayList(baseNames));
        filterProperties(loadedProperties);
        return loadedProperties;
    }

    public Properties load() {
        fileNameStack = propertyLoaderFactory.getEmptyFileNameStack();

        Properties loadedProperties = loadPropertiesFromBaseNameList(this.baseNames);
        filterProperties(loadedProperties);
        return loadedProperties;
    }

    private Properties loadPropertiesFromBaseNameList(List<String> baseNames) {
        Properties loadedProperties = propertyLoaderFactory.getEmptyProperties();
        for (String fileName : propertyFileNameHelper.getFileNames(baseNames, propertySuffix.getSuffixes(), fileExtension)) {
            throwIfRecursionInIncludes(fileName);

            fileNameStack.push(fileName);
            for (PropertyLoaderOpener opener : propertyLocation.getOpeners()) {
                Properties newProperties = propertyFileReader.tryToReadPropertiesFromFile(fileName, propertyFileEncoding, opener);
                Properties includedProperties = loadPropertiesFromBaseNameList(Lists.newArrayList(collectIncludesAndRemoveKey(newProperties)));
                newProperties.putAll(includedProperties);
                loadedProperties.putAll(newProperties);
            }
            fileNameStack.pop();
        }
        return loadedProperties;
    }

    private void throwIfRecursionInIncludes(String fileName) {
        if (fileNameStack.contains(fileName)) {
            StringBuilder sb = propertyLoaderFactory.getStringBuilder();
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
    }

    private String[] collectIncludesAndRemoveKey(Properties properties) {
        String[] includes = new String[]{};
        if (properties.containsKey(INCLUDE_KEY)) {
            includes = properties.getProperty(INCLUDE_KEY).split(",");
            properties.remove(INCLUDE_KEY);
        }
        return includes;
    }

    private void filterProperties(Properties loadedProperties) {
        for (PropertyLoaderFilter filter : propertyLoaderFilters.getFilters()) {
            filter.filter(loadedProperties);
        }
    }
}

