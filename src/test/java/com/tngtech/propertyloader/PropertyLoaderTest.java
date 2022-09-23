package com.tngtech.propertyloader;

import com.tngtech.propertyloader.exception.PropertyLoaderException;
import com.tngtech.propertyloader.impl.*;
import com.tngtech.propertyloader.impl.helpers.HostsHelper;
import com.tngtech.propertyloader.impl.helpers.PropertyFileNameHelper;
import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderOpener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyLoaderTest {

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
    void testWithEncoding() {

    }

    @Test
    void testGetLocations() {
        assertThat(propertyLoader.getLocations()).isSameAs(propertyLocation);
    }

    @Test
    void testGetSuffixes() {
        assertThat(propertyLoader.getSuffixes()).isSameAs(propertySuffix);
    }

    @Test
    void testGetFilters() {
        assertThat(propertyLoader.getFilters()).isSameAs(propertyFilter);
    }

    @Test
    void testGetExtension() {
        assertThat(propertyLoader.getExtension()).isEqualTo("properties");
    }

    @Test
    void testWithSuffixes() {

    }

    @Test
    void testWithLocations() {

    }

    @Test
    void testWithFilters() {

    }

    @Test
    void testWithExtension() {

    }

    @Test
    void testWithBaseNames() {

    }

    @Test
    void testWithDefaultConfig() {
        when(propertyLocation.clear()).thenReturn(propertyLocation);
        when(propertySuffix.clear()).thenReturn(propertySuffix);
        when(propertyFilter.clear()).thenReturn(propertyFilter);

        assertThat(propertyLoader.withDefaultConfig()).isSameAs(propertyLoader);

        verify(propertyLocation).clear();
        verify(propertySuffix).clear();
        verify(propertyFilter).clear();
        verify(propertyLocation).atDefaultLocations();
        verify(propertySuffix).addDefaultSuffixes();
        verify(propertyFilter).withDefaultFilters();
    }

    @Test
    void testAtDefaultLocations() {
        assertThat(propertyLoader.atDefaultLocations()).isSameAs(propertyLoader);
        verify(propertyLocation).atDefaultLocations();
    }

    @Test
    void testAtCurrentDirectory() {
        assertThat(propertyLoader.atCurrentDirectory()).isSameAs(propertyLoader);
        verify(propertyLocation).atCurrentDirectory();
    }

    @Test
    void testAtHomeDirectory() {
        assertThat(propertyLoader.atHomeDirectory()).isSameAs(propertyLoader);
        verify(propertyLocation).atHomeDirectory();
    }

    @Test
    void testAtDirectory() {
        assertThat(propertyLoader.atDirectory("dir")).isSameAs(propertyLoader);
        verify(propertyLocation).atDirectory("dir");
    }

    @Test
    void testAtContextClassPath() {
        assertThat(propertyLoader.atContextClassPath()).isSameAs(propertyLoader);
        verify(propertyLocation).atContextClassPath();
    }

    @Test
    void testAtRelativeToClass() {
        assertThat(propertyLoader.atRelativeToClass(this.getClass())).isSameAs(propertyLoader);
        verify(propertyLocation).atRelativeToClass(this.getClass());
    }

    @Test
    void testFromClassLoader() {
        assertThat(propertyLoader.atClassLoader(this.getClass().getClassLoader())).isSameAs(propertyLoader);
        verify(propertyLocation).atClassLoader(this.getClass().getClassLoader());
    }

    @Test
    void testAtBaseURL() throws Exception {
        URL url = new File("").toURI().toURL();
        assertThat(propertyLoader.atBaseURL(url)).isSameAs(propertyLoader);
        verify(propertyLocation).atBaseURL(url);
    }

    @Test
    void testAddUserName() {
        assertThat(propertyLoader.addUserName()).isSameAs(propertyLoader);
        verify(propertySuffix).addUserName();
    }

    @Test
    void testAddLocalHostNames() {
        assertThat(propertyLoader.addLocalHostNames()).isSameAs(propertyLoader);
        verify(propertySuffix).addLocalHostNames();
    }

    @Test
    void testAddString() {
        assertThat(propertyLoader.addString("suf")).isSameAs(propertyLoader);
        verify(propertySuffix).addString("suf");
    }

    @Test
    void testAddSuffixList() {
        List<String> suf = new ArrayList<>();
        assertThat(propertyLoader.addSuffixList(suf)).isSameAs(propertyLoader);
        verify(propertySuffix).addSuffixList(suf);
    }

    @Test
    void testAddDefaultSuffixes() {
        assertThat(propertyLoader.addDefaultSuffixes()).isSameAs(propertyLoader);
        verify(propertySuffix).addDefaultSuffixes();
    }

    @Test
    void testWithDefaultFilters() {
        assertThat(propertyLoader.withDefaultFilters()).isSameAs(propertyLoader);
        verify(propertyFilter).withDefaultFilters();
    }

    @Test
    void testWithVariableResolvingFilter() {
        assertThat(propertyLoader.withVariableResolvingFilter()).isSameAs(propertyLoader);
        verify(propertyFilter).withVariableResolvingFilter();
    }

    @Test
    void testWithEnvironmentResolvingFilter() {
        assertThat(propertyLoader.withEnvironmentResolvingFilter()).isSameAs(propertyLoader);
        verify(propertyFilter).withEnvironmentResolvingFilter();
    }

    @Test
    void testWithWarnIfPropertyHasToBeDefined() {
        assertThat(propertyLoader.withWarnIfPropertyHasToBeDefined()).isSameAs(propertyLoader);
        verify(propertyFilter).withWarnIfPropertyHasToBeDefined();
    }

    @Test
    void testWithWarnOnSurroundingWhitespace() {
        assertThat(propertyLoader.withWarnOnSurroundingWhitespace()).isSameAs(propertyLoader);
        verify(propertyFilter).withWarnOnSurroundingWhitespace();
    }

    @Test
    void testLoadFromBaseName_Calls_loadPropertiesFromBaseNameList_And_filterProperties() {
        propertyLoader.load("file");
        verify(propertyLoaderFactory).getEmptyFileNameStack();
        verify(propertyLoaderFactory).getEmptyProperties();
        verify(propertyFilter).getFilters();
    }

    @Test
    void testLoadFromBaseNameList_Calls_loadPropertiesFromBaseNameList_And_filterProperties() {
        String[] baseNames = {"file"};
        propertyLoader.load(baseNames);
        verify(propertyLoaderFactory).getEmptyFileNameStack();
        verify(propertyLoaderFactory).getEmptyProperties();
        verify(propertyFilter).getFilters();
    }

    @Test
    void testLoad_Calls_loadPropertiesFromBaseNameList_And_filterProperties() {
        propertyLoader.load();
        verify(propertyLoaderFactory).getEmptyFileNameStack();
        verify(propertyLoaderFactory).getEmptyProperties();
        verify(propertyFilter).getFilters();
    }

    @Test
    void testLoadPropertiesFromBaseNameList_Calls_PropertyFileReader_And_Prevents_StackOverflow() {
        Stack<String> fileNameStack = new Stack<>();
        when(propertyLoaderFactory.getEmptyProperties()).thenReturn(properties);
        when(propertyLoaderFactory.getEmptyFileNameStack()).thenReturn(fileNameStack);
        when(propertyLoaderFactory.getStringBuilder()).thenReturn(new StringBuilder());
        List<String> fileNames = asList("file1.properties", "file2.properties");
        List<String> suffixes = new ArrayList<>();
        when(propertySuffix.getSuffixes()).thenReturn(suffixes);
        when(propertyFileNameHelper.getFileNames(ArgumentMatchers.anyCollection(), ArgumentMatchers.anyCollection(), ArgumentMatchers.anyString())).thenReturn(fileNames);
        when(propertyLocation.getOpeners()).thenReturn(asList(propertyLoaderOpener1, propertyLoaderOpener2));
        when(propertyFileReader.tryToReadPropertiesFromFile(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any(PropertyLoaderOpener.class))).thenReturn(properties);

        assertThatThrownBy(() ->propertyLoader.load())
                .isInstanceOf(PropertyLoaderException.class);
    }
}
