package com.tngtech.propertyloader.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class PropertySuffix {
    private List<String> suffixes = Lists.newArrayList();

    public PropertySuffix addUserName()
    {
        suffixes.add(System.getProperty("user.name"));
        return this;
    }

    public PropertySuffix addHostNames()
    {
        Set<String> hostSet = Sets.newHashSet();

        for (InetAddress host : getHosts()) {
            hostSet.add(host.getHostName());
        }

        List<String> hostNames = Lists.newArrayList(hostSet);
        Collections.sort(hostNames);
        suffixes.addAll(hostNames);
        return this;
    }

    public PropertySuffix addString(String suffix)
    {
        suffixes.add(suffix);
        return this;
    }



    public List<String> getSuffixes()
    {
        return suffixes;
    }

    public PropertySuffix defaultConfig() {

        addUserName();
        addHostNames();
        addString("override");
        return this;
    }


    private InetAddress[] getHosts() {
        InetAddress in;

        try {
            in = InetAddress.getLocalHost();
        } catch (UnknownHostException uE) {
            return new InetAddress[0];
        }

        try {
            return InetAddress.getAllByName(in.getHostName());
        } catch (UnknownHostException uE) {
            return new InetAddress[]{in};
        }
    }

    public void setSuffixes(List<String> suffixes) {
        this.suffixes = suffixes;
    }
}
