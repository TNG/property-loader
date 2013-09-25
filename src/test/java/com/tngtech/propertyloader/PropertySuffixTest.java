package com.tngtech.propertyloader;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.impl.DefaultPropertySuffix;
import com.tngtech.propertyloader.impl.helpers.HostsHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertySuffixTest {

    private DefaultPropertySuffix propertySuffix;
    private List<String> list;
    String[] testAddLocalHostNamesAndAddSuffixList = {"testAddLocalHostNames","testAddSuffixList"};


    @Mock
    private HostsHelper hostshelper;

    @Before
    public void SetUp() {
        list = Lists.newArrayList(testAddLocalHostNamesAndAddSuffixList);
        when(hostshelper.getLocalHostNames()).thenReturn(list);
    }

    @Test
    public void testAddUserName() {
        propertySuffix = new DefaultPropertySuffix((hostshelper));
        propertySuffix.addString("testThatAddMethodDoesNotClearList");
        assertEquals(propertySuffix, propertySuffix.addUserName());
        assertTrue(propertySuffix.getSuffixes().contains(System.getProperty("user.name")));
        assertTrue(propertySuffix.getSuffixes().contains("testThatAddMethodDoesNotClearList"));
    }

    @Test
    public void testAddLocalHostNames() {
        propertySuffix = new DefaultPropertySuffix((hostshelper));
        propertySuffix.addString("testThatAddMethodDoesNotClearList");
        assertEquals(propertySuffix, propertySuffix.addLocalHostNames());
        assertTrue(propertySuffix.getSuffixes().contains("testAddLocalHostNames"));
        assertTrue(propertySuffix.getSuffixes().contains("testThatAddMethodDoesNotClearList"));
    }
    @Test
    public void testAddString() {
        propertySuffix = new DefaultPropertySuffix((hostshelper));
        propertySuffix.addString("testThatAddMethodDoesNotClearList");
        assertEquals(propertySuffix, propertySuffix.addString("testAddString"));
        assertTrue(propertySuffix.getSuffixes().contains("testAddString"));
        assertTrue(propertySuffix.getSuffixes().contains("testThatAddMethodDoesNotClearList"));
    }
    @Test
    public void testAddSuffixList() {
        propertySuffix = new DefaultPropertySuffix((hostshelper));
        propertySuffix.addString("testThatAddMethodDoesNotClearList");
        assertEquals(propertySuffix, propertySuffix.addSuffixList(list));
        assertTrue(propertySuffix.getSuffixes().contains("testAddSuffixList"));
        assertTrue(propertySuffix.getSuffixes().contains("testThatAddMethodDoesNotClearList"));
    }
    @Test
    public void testAddDefaultConfig() {
        propertySuffix = new DefaultPropertySuffix((hostshelper));
        propertySuffix.addString("testThatAddMethodDoesNotClearList");
        assertEquals(propertySuffix, propertySuffix.addDefaultSuffixes());
        assertTrue(propertySuffix.getSuffixes().contains("testAddLocalHostNames"));
        assertTrue(propertySuffix.getSuffixes().contains(System.getProperty("user.name")));
        assertTrue(propertySuffix.getSuffixes().contains("override"));
        assertTrue(propertySuffix.getSuffixes().contains("testThatAddMethodDoesNotClearList"));
    }


}
