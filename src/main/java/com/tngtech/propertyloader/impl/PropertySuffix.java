package com.tngtech.propertyloader.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class PropertySuffix {
    private List<String> suffixes = new ArrayList<String>();

    public PropertySuffix addUserName()
    {
        suffixes.add(System.getProperty("user.name"));
        return this;
    }

    public PropertySuffix addHostNames()
    {
        Set<String> hostSet = new HashSet<String>();

        for (InetAddress host : getHosts()) {
            hostSet.add(host.getHostName());
        }

        List<String> hostNames = new ArrayList<String>(hostSet);
        Collections.sort(hostNames);
        suffixes.addAll(hostNames);
        return this;
    }

    public PropertySuffix addString(String suffix)
    {
        suffixes.add(suffix);
        return this;
    }

    public List<String> getFileNames(List<String> baseNames, String fileExtension)
    {
        List<String> fileNameList = new ArrayList<String>();
        for (String baseName : baseNames)
        {
            fileNameList.add(baseName + "." + fileExtension);
            for (String suffix : this.getSuffixes())
            {
                fileNameList.add(baseName + "." + suffix + "." + fileExtension);
            }
        }
        return fileNameList;
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


    static void addIPs(List<String> suffixes) {
        List<String> ips = new ArrayList<String>();

        for (InetAddress host : getHosts()) {
            ips.add(host.getHostAddress());
        }

        Collections.sort(ips);
        suffixes.addAll(ips);
    }


    private static InetAddress[] getHosts() {
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
