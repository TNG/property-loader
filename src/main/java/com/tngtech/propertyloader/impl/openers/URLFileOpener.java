package com.tngtech.propertyloader.impl.openers;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderOpener;

public class URLFileOpener implements PropertyLoaderOpener {

    private URL url;

    public URLFileOpener() {
        try {
            this.url = new File("").toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(String.format("THIS CAN NOT HAPPEN: error while forming URL from path '%s'", ""), e);
        }
    }

    public URLFileOpener(URL url) {
        this.url = url;
    }

    public URLFileOpener(String address) {
        try {
            this.url = new File(address.replace("/", File.separator)).toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(String.format("THIS SHOULD NOT HAPPEN: error while forming URL from path '%s'", address), e);
        }
    }

    public InputStream open(String fileName){

        try {
            URL urlToFile = new URL(url, fileName);
            return urlToFile.openStream();
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(String.format("error while forming new URL from URL %s and filename %s", url.getPath(), fileName), e);
        }catch (IOException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "in path " + url.getPath();
    }
}