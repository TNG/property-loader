package com.tngtech.propertyloader.impl.openers;


import com.tngtech.propertyloader.impl.PropertyLoaderOpener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class WebOpener implements PropertyLoaderOpener {
    private String address;

    public InputStream open(String fileName) throws IOException{
        URL url = new URL(address + fileName);
        return url.openStream();
    }
}
