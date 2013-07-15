package com.tngtech.propertyloader;

import com.tngtech.infrastructure.exception.PreconditionException;
import com.tngtech.propertyloader.impl.openers.OpenerConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PropertyLoaderTest {
    private PropertyLoader loader;

    @Before
    public void setUp() {
        loader = PropertyUtil.defaultConfig();

        loader.getOpeners().add(OpenerConfig.relativeTo(PropertyLoaderTest.class));
    }

    @Test(expected = PreconditionException.class)
    public void testLoadWithEmptyBaseName() {
        loader.load();
    }

    @Test
    public void testMultipleBaseNameLoad() {
        loader.setExtension("properties");
        loader.addBaseName("demoapp-configuration");
        loader.addBaseName("umlauts");
        loader.addBaseName("demoapp-configuration-second");

        Properties props = loader.load();

        assertEquals("externalsystem.url not resolved", "http://production", props.getProperty("coresystem.url"));
        assertEquals("variable replacement does not work", props.getProperty("c"), "Hello, World!");
        assertEquals("include does not work", "yes", props.getProperty("xxx"));
        assertEquals("include does not work (2) -- maybe someone stole the include prefix for the property 'testInclude'?!", "prod-blub", props.getProperty("testInclude"));
        assertThat(props.getProperty("umlauts"), is("ä ö ü Ä Ö Ü ß"));
    }

    @Test
    public void testSingleBaseNameLoad() {
        loader.setExtension("properties");
        loader.addBaseName("demoapp-configuration");

        Properties props = loader.load();

        assertSingleDemoAppConfig(props);
    }

    @Test
    public void testVariableExpansion() {
        Properties props = loader.load("demoapp-configuration", "properties");

        assertSingleDemoAppConfig(props);
    }

    private void assertSingleDemoAppConfig(Properties props) {
        assertEquals("externalsystem.url not resolved", "http://development", props.getProperty("coresystem.url"));
        assertEquals("variable replacement does not work", props.getProperty("c"), "Hello, World!");
        assertEquals("include does not work", "yes", props.getProperty("xxx"));
        assertEquals("include does not work (2) -- maybe someone stole the include prefix for the property 'testInclude'?!", "blub", props.getProperty("testInclude"));
    }

    @Test
    public void testPropertyLoadingWithTwoDots() {
        Properties props = loader.load("abc.def", "properties");

        assertEquals("config files with dots are not working", "def", props.getProperty("abc"));
    }

    @Test
    public void testDecryption() {
        Properties props = loader.load("decryptiontest", "properties");

        assertEquals("decryption is not working", "testqa", props.getProperty("teststring"));

    }

    @Test
    public void testUmlauts() {
        Properties props = loader.load("umlauts", "properties");

        assertEquals("ä ö ü Ä Ö Ü ß", props.getProperty("umlauts"));
    }
}
