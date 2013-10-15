package com.tngtech.propertyloader.impl.helpers;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class HostsHelper {

    public List<String> getLocalHostNames() {

        Set<String> hostSet = Sets.newHashSet();

        for (InetAddress host : getLocalHosts()) {
            hostSet.add(host.getHostName());
        }

        List<String> hostNames = Lists.newArrayList(hostSet);
        Collections.sort(hostNames);

        return hostNames;
    }

    private InetAddress[] getLocalHosts() {
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
}
