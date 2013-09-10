package com.tngtech.propertyloader.impl.openers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.tngtech.propertyloader.impl.PropertyLoaderOpener;

public class FilesystemOpener implements PropertyLoaderOpener {
    private final String prefix;

    public FilesystemOpener() {
        this.prefix = "";
    }

    FilesystemOpener(String prefix) {
        if (! prefix.endsWith("/")) {
            prefix = prefix + "/";
        }
        this.prefix = prefix;
    }

    public InputStream open(String filename) throws FileNotFoundException {
        filename = prefix + filename;
        String osFilename = filename.replace("/", File.separator);
        File file = new File(osFilename);
        if (file.exists()) {
            return new FileInputStream(file);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        if (prefix == "") {
            return "current directory";
        } else {
            return "directory " + prefix;
        }
    }
}
