package com.tngtech.propertyloader.impl.openers;

import java.io.InputStream;

import com.tngtech.propertyloader.impl.PropertyLoaderOpener;
import org.apache.log4j.Logger;

public class RelativeToClassOpener implements PropertyLoaderOpener {

    private final Class<?> reference;

    public RelativeToClassOpener(Class<?> reference) {
        this.reference = reference;
    }

    public InputStream open(String filename) {
        return reference.getResourceAsStream(filename);
    }

    @Override
    public String toString() {
        return "near class " + reference;
    }
}
