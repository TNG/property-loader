package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.helpers.HostsHelper;
import com.tngtech.propertyloader.impl.interfaces.PropertySuffixContainer;

import java.util.ArrayList;
import java.util.List;

public class DefaultPropertySuffixContainer implements PropertySuffixContainer<DefaultPropertySuffixContainer> {

    private final HostsHelper hostsHelper;

    private List<String> suffixes = new ArrayList<String>();

    public DefaultPropertySuffixContainer(HostsHelper hostsHelper) {
        this.hostsHelper = hostsHelper;
    }

    public DefaultPropertySuffixContainer addUserName() {
        this.suffixes.add(System.getProperty("user.name"));
        return this;
    }

    public DefaultPropertySuffixContainer addLocalHostNames() {
        this.suffixes.addAll(hostsHelper.getLocalHostNames());
        return this;
    }

    public DefaultPropertySuffixContainer addString(String suffix) {
        this.suffixes.add(suffix);
        return this;
    }

    public DefaultPropertySuffixContainer addSuffixList(List<String> suffixes) {
        this.suffixes.addAll(suffixes);
        return this;
    }

    public List<String> getSuffixes() {
        return suffixes;
    }

    public DefaultPropertySuffixContainer addDefaultSuffixes() {

        addUserName();
        addLocalHostNames();
        addString("override");
        return this;
    }

    public DefaultPropertySuffixContainer clear() {
        suffixes.clear();
        return this;
    }


}
