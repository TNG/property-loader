package com.tngtech.propertyloader.impl;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.impl.helpers.HostsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope("prototype")
public class PropertySuffix {

    private final HostsHelper hostsHelper;

    private List<String> suffixes = Lists.newArrayList();

    @Autowired
    public PropertySuffix(HostsHelper hostsHelper) {
        this.hostsHelper = hostsHelper;
    }

    public PropertySuffix addUserName()
    {
        this.suffixes.add(System.getProperty("user.name"));
        return this;
    }

    public PropertySuffix addLocalHostNames()
    {
        this.suffixes.addAll(hostsHelper.getLocalHostNames());
        return this;
    }

    public PropertySuffix addString(String suffix)
    {
        this.suffixes.add(suffix);
        return this;
    }

    public PropertySuffix addSuffixList(List<String> suffixes) {
        this.suffixes.addAll(suffixes);
        return this;
    }

    public List<String> getSuffixes()
    {
        return suffixes;
    }

    public PropertySuffix addDefaultConfig() {

        addUserName();
        addLocalHostNames();
        addString("override");
        return this;
    }

    public void clear() {
        suffixes.clear();
    }



}
