package com.tngtech.propertyloader.impl;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

@Component
public class PropertyLoaderFactory {

    public Properties getEmptyProperties(){
        return new Properties();
    }

    public InputStreamReader getInputStreamReader(InputStream stream, String encoding) throws IOException {
        return new InputStreamReader(stream, encoding);
    }
}
