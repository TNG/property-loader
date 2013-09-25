package com.tngtech.propertyloader.impl;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.impl.helpers.HostsHelper;
import com.tngtech.propertyloader.impl.interfaces.PropertySuffix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope("prototype")
public class DefaultPropertySuffix implements PropertySuffix<DefaultPropertySuffix> {

    private final HostsHelper hostsHelper;

    private List<String> suffixes = Lists.newArrayList();

    @Autowired
    public DefaultPropertySuffix(HostsHelper hostsHelper) {
        this.hostsHelper = hostsHelper;
    }

    public DefaultPropertySuffix addUserName()
    {
        this.suffixes.add(System.getProperty("user.name"));
        return this;
    }

    public DefaultPropertySuffix addLocalHostNames()
    {
        this.suffixes.addAll(hostsHelper.getLocalHostNames());
        return this;
    }

    public DefaultPropertySuffix addString(String suffix)
    {
        this.suffixes.add(suffix);
        return this;
    }

    public DefaultPropertySuffix addSuffixList(List<String> suffixes) {
        this.suffixes.addAll(suffixes);
        return this;
    }

    public List<String> getSuffixes()
    {
        return suffixes;
    }

    public DefaultPropertySuffix addDefaultSuffixes() {

        addUserName();
        addLocalHostNames();
        addString("override");
        return this;
    }

    public DefaultPropertySuffix clear() {
        suffixes.clear();
        return this;
    }



}
