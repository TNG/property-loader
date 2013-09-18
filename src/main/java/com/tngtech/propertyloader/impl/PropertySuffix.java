package com.tngtech.propertyloader.impl;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.impl.helpers.HostsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PropertySuffix {

    private final HostsHelper hostsHelper;

    private List<String> suffixes = Lists.newArrayList();

    @Autowired
    public PropertySuffix(HostsHelper hostsHelper) {
        this.hostsHelper = hostsHelper;
    }

    public PropertySuffix addUserName()
    {
        suffixes.add(System.getProperty("user.name"));
        return this;
    }

    public PropertySuffix addHostNames()
    {
        suffixes.addAll(hostsHelper.getLocalHostNames());
        return this;
    }

    public PropertySuffix addString(String suffix)
    {
        suffixes.add(suffix);
        return this;
    }

    public PropertySuffix addSuffixList(List<String> suffixes) {
        suffixes.addAll(suffixes);
        return this;
    }

    public List<String> getSuffixes()
    {
        return suffixes;
    }

    public PropertySuffix addDefaultConfig() {

        addUserName();
        addHostNames();
        addString("override");
        return this;
    }



}
