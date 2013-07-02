package com.tngtech.propertyloader.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tngtech.infrastructure.startupshutdown.SystemLog;

import org.apache.commons.lang3.StringUtils;

public abstract class SuffixConfig {
    /**
     * Set the additional profile name with <code>-DsystemProfileName=myProfile</code>
     */
    public static final String PROFILE_NAME_SYSTEM_PROPERTY_KEY = "systemProfileName";

    private static volatile String extraSuffix = null;

    public static void setExtraSuffix(String extraSuffix) {
        SuffixConfig.extraSuffix = extraSuffix;
    }

    public static List<String> defaultConfig() {
        List<String> result = new ArrayList<String>();

        addExtraSuffix(result);
        addHostnames(result);
        // IPs not enabled for now
        result.add(userName());
        addProfile(result);
        result.add("override");

        return result;
    }

    static void addExtraSuffix(List<String> suffixes) {
        if (!StringUtils.isBlank(extraSuffix)) {
            suffixes.add(extraSuffix);
        }
    }

    static void addProfile(List<String> suffixes) {
        String profileName = System.getProperty(PROFILE_NAME_SYSTEM_PROPERTY_KEY);
        if (!StringUtils.isBlank(profileName)) {
            suffixes.add(profileName.trim());
        }
    }

    static void addHostnames(List<String> suffixes) {
        Set<String> hostSet = new HashSet<String>();

        for (InetAddress host : getHosts()) {
            hostSet.add(host.getHostName());
        }

        List<String> hostNames = new ArrayList<String>(hostSet);
        Collections.sort(hostNames);
        suffixes.addAll(hostNames);
    }

    static void addIPs(List<String> suffixes) {
        List<String> ips = new ArrayList<String>();

        for (InetAddress host : getHosts()) {
            ips.add(host.getHostAddress());
        }

        Collections.sort(ips);
        suffixes.addAll(ips);
    }

    static String userName() {
        return System.getProperty("user.name");
    }

    private static InetAddress[] getHosts() {
        InetAddress in;

        try {
            in = InetAddress.getLocalHost();
        } catch (UnknownHostException uE) {
            SystemLog.error("WARNING: unknown host exception, this might result in not proper loading of resources with your host information! Exception: " + uE, uE);
            return new InetAddress[0];
        }

        try {
            return InetAddress.getAllByName(in.getHostName());
        } catch (UnknownHostException uE) {
            SystemLog.error("WARNING: unknown host exception, this might result in not proper loading of resources with your host information! Exception: " + uE, uE);
            return new InetAddress[]{in};
        }
    }
}
