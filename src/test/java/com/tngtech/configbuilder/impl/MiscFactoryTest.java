package com.tngtech.configbuilder.impl;

import com.tngtech.propertyloader.PropertyLoader;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MiscFactoryTest {

    private MiscFactory miscFactory;

    @Before
    public void setUp() throws Exception {
        miscFactory = new MiscFactory();
    }

    @Test
    public void testCreateCommandLineParser() throws Exception {
        assertEquals(GnuParser.class,miscFactory.createCommandLineParser().getClass());
    }

    @Test
    public void testCreateOptions() throws Exception {
        assertEquals(Options.class,miscFactory.createOptions().getClass());
    }

    @Test
    public void testCreatePropertyLoader() throws Exception {
        assertEquals(PropertyLoader.class,miscFactory.createPropertyLoader().getClass());
    }

    @Test
    public void testCreateStringBuilder() throws Exception {
        assertEquals(StringBuilder.class,miscFactory.createStringBuilder().getClass());
    }
}
