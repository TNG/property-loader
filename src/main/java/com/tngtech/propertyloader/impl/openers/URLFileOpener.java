package com.tngtech.propertyloader.impl.openers;

import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderOpener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Searches for properties files at a file system path.
 * The search path can be provided as a String or an URL.
 * Use the empty constructor to search the current directory.
 */
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

    /**
     * Tries to open the given file.
     * A filename that starts with '/' is understood as an absolute path,
     * i.e. the URLFileOpener forgets about its path.
     *
     * @param fileName the filename
     * @return
     */
    public InputStream open(String fileName) {

        try {
            URL urlToFile = new URL(url, fileName);
            return urlToFile.openStream();
        } catch (MalformedURLException e) {
            throw new RuntimeException(String.format("error while forming new URL from URL %s and filename %s", url.getPath(), fileName), e);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "in path " + url.getPath();
    }
}