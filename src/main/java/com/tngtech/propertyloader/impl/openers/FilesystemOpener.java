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

    public FilesystemOpener(String prefix) {
        if (! prefix.endsWith("/")) {
            prefix = prefix + "/";
        }
        this.prefix = prefix;
    }

    public InputStream open(String fileName){
        fileName = prefix + fileName;
        String osFilename = fileName.replace("/", File.separator);
        File file = new File(osFilename);
        if (file.exists()) {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new OpenerException(String.format("error while creating inputstream from file '%s'", fileName), e);
            }
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
