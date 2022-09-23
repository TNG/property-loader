package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.helpers.HostsHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DefaultPropertySuffixContainerTest {

    private DefaultPropertySuffixContainer propertySuffix;
    private List<String> localHostNames = asList("testAddLocalHostNames", "testAddSuffixList");

    @Mock
    private HostsHelper hostshelper;

    @BeforeEach
    void setUp() {
        when(hostshelper.getLocalHostNames()).thenReturn(localHostNames);
        propertySuffix = new DefaultPropertySuffixContainer((hostshelper));
        propertySuffix.addString("testThatAddMethodDoesNotClearList");
    }

    @Test
    void testAddUserName() {
        assertThat(propertySuffix.addUserName()).isSameAs(propertySuffix);
        assertThat(propertySuffix.getSuffixes()).contains(System.getProperty("user.name"), "testThatAddMethodDoesNotClearList");
    }

    @Test
    void testAddLocalHostNames() {
        assertThat(propertySuffix.addLocalHostNames()).isSameAs(propertySuffix);
        assertThat(propertySuffix.getSuffixes()).contains("testAddLocalHostNames", "testThatAddMethodDoesNotClearList");
    }

    @Test
    void testAddString() {
        assertThat(propertySuffix.addString("testAddString")).isSameAs(propertySuffix);
        assertThat(propertySuffix.getSuffixes()).contains("testAddString", "testThatAddMethodDoesNotClearList");
    }

    @Test
    void testAddSuffixList() {
        assertThat(propertySuffix.addSuffixList(localHostNames)).isSameAs(propertySuffix);
        assertThat(propertySuffix.getSuffixes()).contains("testAddSuffixList", "testThatAddMethodDoesNotClearList");
    }

    @Test
    void testGetSuffixes() {

    }

    @Test
    void testAddDefaultSuffixes() {

    }

    @Test
    void testClear() {

    }
}
