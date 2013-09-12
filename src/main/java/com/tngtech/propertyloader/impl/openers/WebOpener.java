package com.tngtech.propertyloader.impl.openers;


import com.tngtech.propertyloader.impl.PropertyLoaderOpener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class WebOpener implements PropertyLoaderOpener {
    private String address;

    public WebOpener(URL url){
        this.address = url.toString();
    }

    public WebOpener(String address){
        this.address = address;
    }

    public InputStream open(String fileName){
        URL url = null;
        try {
            url = new URL(address + fileName);
        } catch (MalformedURLException e) {
            throw new WebOpenerException(address + fileName + "is not a valid URL", e);
        }
        try {
            return url.openStream();
        } catch (IOException e) {
            return null;
        }
    }
}
