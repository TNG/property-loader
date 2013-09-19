package com.tngtech.propertyloader.impl.openers;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import com.tngtech.propertyloader.impl.PropertyLoaderOpener;

public class URLFileOpener implements PropertyLoaderOpener {
    private URL url;

    public URLFileOpener() {
        try {
            this.url = new File("").toURI().toURL();
        } catch (MalformedURLException e) {
            throw new OpenerException(String.format("error while getting URL from path '%s'", ""), e);
        }
    }

    public URLFileOpener(URL url) {
        this.url = url;
    }

    public URLFileOpener(String address) {
        try {
            this.url = new File(address.replace("/", File.separator)).toURI().toURL();
        } catch (MalformedURLException e) {
            throw new OpenerException(String.format("error while getting URL from path '%s'", address), e);
        }
    }

    public InputStream open(String fileName){

        try {
            url = new URL(url, fileName);
            return url.openStream();
        }
        catch (MalformedURLException e) {
            throw new OpenerException(String.format("error while getting URL"), e);
        }catch (IOException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        if (url.equals("")) {
            return "current directory";
        } else {
            return "directory " + url;
        }
    }
}