package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.helpers.HostsHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPropertySuffixContainerTest {

    private DefaultPropertySuffixContainer propertySuffix;
    private List<String> localHostNames = asList("testAddLocalHostNames", "testAddSuffixList");

    @Mock
    private HostsHelper hostshelper;

    @Before
    public void setUp() {
        when(hostshelper.getLocalHostNames()).thenReturn(localHostNames);
        propertySuffix = new DefaultPropertySuffixContainer((hostshelper));
        propertySuffix.addString("testThatAddMethodDoesNotClearList");
    }

    @Test
    public void testAddUserName() {
        assertThat(propertySuffix.addUserName()).isSameAs(propertySuffix);
        assertThat(propertySuffix.getSuffixes()).contains(System.getProperty("user.name"), "testThatAddMethodDoesNotClearList");
    }

    @Test
    public void testAddLocalHostNames() {
        assertThat(propertySuffix.addLocalHostNames()).isSameAs(propertySuffix);
        assertThat(propertySuffix.getSuffixes()).contains("testAddLocalHostNames", "testThatAddMethodDoesNotClearList");
    }

    @Test
    public void testAddString() {
        assertThat(propertySuffix.addString("testAddString")).isSameAs(propertySuffix);
        assertThat(propertySuffix.getSuffixes()).contains("testAddString", "testThatAddMethodDoesNotClearList");
    }

    @Test
    public void testAddSuffixList() {
        assertThat(propertySuffix.addSuffixList(localHostNames)).isSameAs(propertySuffix);
        assertThat(propertySuffix.getSuffixes()).contains("testAddSuffixList", "testThatAddMethodDoesNotClearList");
    }

    @Test
    public void testGetSuffixes() {

    }

    @Test
    public void testAddDefaultSuffixes() {

    }

    @Test
    public void testClear() {

    }
}
