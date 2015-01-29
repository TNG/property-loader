package com.tngtech.propertyloader;

import com.tngtech.propertyloader.exception.PropertyLoaderException;
import com.tngtech.propertyloader.impl.*;
import com.tngtech.propertyloader.impl.helpers.HostsHelper;
import com.tngtech.propertyloader.impl.helpers.PropertyFileNameHelper;
import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderOpener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertyLoaderTest {

    @InjectMocks
    private PropertyLoader propertyLoader;
    @Mock
    private PropertyLoaderFactory propertyLoaderFactory;
    @Mock
    private PropertyFileNameHelper propertyFileNameHelper;
    @Mock
    private PropertyFileReader propertyFileReader;
    @Mock
    private HostsHelper hostsHelper;
    @Mock
    private Properties properties;
    @Mock
    private DefaultPropertySuffixContainer propertySuffix;
    @Mock
    private DefaultPropertyLocationContainer propertyLocation;
    @Mock
    private DefaultPropertyFilterContainer propertyFilter;
    @Mock
    private PropertyLoaderOpener propertyLoaderOpener1;
    @Mock
    private PropertyLoaderOpener propertyLoaderOpener2;

    @Test
    public void testWithEncoding() {

    }

    @Test
    public void testGetLocations() {
        assertEquals(propertyLocation, propertyLoader.getLocations());
    }

    @Test
    public void testGetSuffixes() {
        assertEquals(propertySuffix, propertyLoader.getSuffixes());
    }

    @Test
    public void testGetFilters() {
        assertEquals(propertyFilter, propertyLoader.getFilters());
    }

    @Test
    public void testGetExtension() {
        assertEquals("properties", propertyLoader.getExtension());
    }

    @Test
    public void testWithSuffixes() {

    }

    @Test
    public void testWithLocations() {

    }

    @Test
    public void testWithFilters() {

    }

    @Test
    public void testWithExtension() {

    }

    @Test
    public void testWithBaseNames() {

    }

    @Test
    public void testWithDefaultConfig() {
        when(propertyLocation.clear()).thenReturn(propertyLocation);
        when(propertySuffix.clear()).thenReturn(propertySuffix);
        when(propertyFilter.clear()).thenReturn(propertyFilter);

        assertEquals(propertyLoader, propertyLoader.withDefaultConfig());

        verify(propertyLocation).clear();
        verify(propertySuffix).clear();
        verify(propertyFilter).clear();
        verify(propertyLocation).atDefaultLocations();
        verify(propertySuffix).addDefaultSuffixes();
        verify(propertyFilter).withDefaultFilters();
    }

    @Test
    public void testAtDefaultLocations() {
        assertEquals(propertyLoader, propertyLoader.atDefaultLocations());
        verify(propertyLocation).atDefaultLocations();
    }

    @Test
    public void testAtCurrentDirectory() {
        assertEquals(propertyLoader, propertyLoader.atCurrentDirectory());
        verify(propertyLocation).atCurrentDirectory();
    }

    @Test
    public void testAtHomeDirectory() {
        assertEquals(propertyLoader, propertyLoader.atHomeDirectory());
        verify(propertyLocation).atHomeDirectory();
    }

    @Test
    public void testAtDirectory() {
        assertEquals(propertyLoader, propertyLoader.atDirectory("dir"));
        verify(propertyLocation).atDirectory("dir");
    }

    @Test
    public void testAtContextClassPath() {
        assertEquals(propertyLoader, propertyLoader.atContextClassPath());
        verify(propertyLocation).atContextClassPath();
    }

    @Test
    public void testAtRelativeToClass() {
        assertEquals(propertyLoader, propertyLoader.atRelativeToClass(this.getClass()));
        verify(propertyLocation).atRelativeToClass(this.getClass());
    }

    @Test
    public void testFromClassLoader() {
        assertEquals(propertyLoader, propertyLoader.atClassLoader(this.getClass().getClassLoader()));
        verify(propertyLocation).atClassLoader(this.getClass().getClassLoader());
    }

    @Test
    public void testAtBaseURL() throws Exception {
        URL url = new File("").toURI().toURL();
        assertEquals(propertyLoader, propertyLoader.atBaseURL(url));
        verify(propertyLocation).atBaseURL(url);
    }

    @Test
    public void testAddUserName() {
        assertEquals(propertyLoader, propertyLoader.addUserName());
        verify(propertySuffix).addUserName();
    }

    @Test
    public void testAddLocalHostNames() {
        assertEquals(propertyLoader, propertyLoader.addLocalHostNames());
        verify(propertySuffix).addLocalHostNames();
    }

    @Test
    public void testAddString() {
        assertEquals(propertyLoader, propertyLoader.addString("suf"));
        verify(propertySuffix).addString("suf");
    }

    @Test
    public void testAddSuffixList() {
        List<String> suf = new ArrayList<String>();
        assertEquals(propertyLoader, propertyLoader.addSuffixList(suf));
        verify(propertySuffix).addSuffixList(suf);
    }

    @Test
    public void testAddDefaultSuffixes() {
        assertEquals(propertyLoader, propertyLoader.addDefaultSuffixes());
        verify(propertySuffix).addDefaultSuffixes();
    }

    @Test
    public void testWithDefaultFilters() {
        assertEquals(propertyLoader, propertyLoader.withDefaultFilters());
        verify(propertyFilter).withDefaultFilters();
    }

    @Test
    public void testWithVariableResolvingFilter() {
        assertEquals(propertyLoader, propertyLoader.withVariableResolvingFilter());
        verify(propertyFilter).withVariableResolvingFilter();
    }

    @Test
    public void testWithEnvironmentResolvingFilter() {
        assertEquals(propertyLoader, propertyLoader.withEnvironmentResolvingFilter());
        verify(propertyFilter).withEnvironmentResolvingFilter();
    }

    @Test
    public void testWithWarnIfPropertyHasToBeDefined() {
        assertEquals(propertyLoader, propertyLoader.withWarnIfPropertyHasToBeDefined());
        verify(propertyFilter).withWarnIfPropertyHasToBeDefined();
    }

    @Test
    public void testWithWarnOnSurroundingWhitespace() {
        assertEquals(propertyLoader, propertyLoader.withWarnOnSurroundingWhitespace());
        verify(propertyFilter).withWarnOnSurroundingWhitespace();
    }

    @Test
    public void testLoadFromBaseName_Calls_loadPropertiesFromBaseNameList_And_filterProperties() {
        propertyLoader.load("file");
        verify(propertyLoaderFactory).getEmptyFileNameStack();
        verify(propertyLoaderFactory).getEmptyProperties();
        verify(propertyFilter).getFilters();
    }

    @Test
    public void testLoadFromBaseNameList_Calls_loadPropertiesFromBaseNameList_And_filterProperties() {
        String[] baseNames = {"file"};
        propertyLoader.load(baseNames);
        verify(propertyLoaderFactory).getEmptyFileNameStack();
        verify(propertyLoaderFactory).getEmptyProperties();
        verify(propertyFilter).getFilters();
    }

    @Test
    public void testLoad_Calls_loadPropertiesFromBaseNameList_And_filterProperties() {
        propertyLoader.load();
        verify(propertyLoaderFactory).getEmptyFileNameStack();
        verify(propertyLoaderFactory).getEmptyProperties();
        verify(propertyFilter).getFilters();
    }

    @Test(expected = PropertyLoaderException.class)
    public void testLoadPropertiesFromBaseNameList_Calls_PropertyFileReader_And_Prevents_StackOverflow() {
        Stack<String> fileNameStack = new Stack<String>();
        when(propertyLoaderFactory.getEmptyProperties()).thenReturn(properties);
        when(propertyLoaderFactory.getEmptyFileNameStack()).thenReturn(fileNameStack);
        when(propertyLoaderFactory.getStringBuilder()).thenReturn(new StringBuilder());
        List<String> fileNames = Arrays.asList("file1.properties", "file2.properties");
        ArrayList<String> suffixes = new ArrayList<String>();
        when(propertySuffix.getSuffixes()).thenReturn(suffixes);
        when(propertyFileNameHelper.getFileNames(Matchers.anyCollection(), Matchers.anyCollection(), Matchers.anyString())).thenReturn(fileNames);
        when(propertyLocation.getOpeners()).thenReturn(Arrays.<PropertyLoaderOpener>asList(propertyLoaderOpener1, propertyLoaderOpener2));
        when(propertyFileReader.tryToReadPropertiesFromFile(Matchers.anyString(), Matchers.anyString(), Matchers.any(PropertyLoaderOpener.class))).thenReturn(properties);

        propertyLoader.load();

        verify(propertyFileReader).tryToReadPropertiesFromFile("file1.properties", "ISO-8859-1", propertyLoaderOpener1);
        verify(propertyFileReader).tryToReadPropertiesFromFile("file2.properties", "ISO-8859-1", propertyLoaderOpener1);
        verify(propertyFileReader).tryToReadPropertiesFromFile("file1.properties", "ISO-8859-1", propertyLoaderOpener2);
        verify(propertyFileReader).tryToReadPropertiesFromFile("file2.properties", "ISO-8859-1", propertyLoaderOpener2);
        verify(properties, times(4)).putAll(properties);
    }
}
