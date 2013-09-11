package com.tngtech.propertyloader;

import com.tngtech.propertyloader.impl.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

public class PropertyLoader {
    private String propertyFileEncoding = "ISO-8859-1";
    private List<String> baseNames;
    private String fileExtension;
    private SuffixConfig suffixConfig;
    private OpenerConfig openerConfig;

    public PropertyLoader() {
    }

    public void setEncoding(String propertyFileEncoding) {
        this.propertyFileEncoding = propertyFileEncoding;
    }

    public SuffixConfig getSuffixes() {
        return suffixConfig;
    }

    public void setSuffixConfig(SuffixConfig suffixConfig) {
        this.suffixConfig = suffixConfig;
    }

    public OpenerConfig getOpeners() {
        return openerConfig;
    }

    public void setOpenerConfig(OpenerConfig openerConfig) {
        this.openerConfig = openerConfig;
    }

    public String getExtension() {
        return fileExtension;
    }

    public void setExtension(String extension) {
        this.fileExtension = extension;
    }

    public void setBaseNames(List<String> baseNames) {
        this.baseNames = baseNames;
    }

    public void addBaseName(String baseName) {
        baseNames.add(baseName);
    }

    public Properties loadProperties(String baseName, String extension) {
        addBaseName(baseName);
        fileExtension = extension;
        return loadProperties();
    }

    public Properties loadProperties(String[] baseNames, String extension) {
        for(String baseName : baseNames)
        {
            addBaseName(baseName);
        }
        fileExtension = extension;
        return loadProperties();
    }

    public Properties loadProperties(){

        Properties loadedProperties = new Properties();
        for (String filename : suffixConfig.getFileNames(baseNames, fileExtension))
        {
            for (PropertyLoaderOpener opener : openerConfig.getOpeners())
            {
                loadOrderedPropertiesFromFile(filename, opener, loadedProperties);

            }
        }
        return loadedProperties;
    }

    private void loadOrderedPropertiesFromFile(String fileName, PropertyLoaderOpener opener, Properties loadedProperties)
    {
        try{
            InputStream stream = opener.open(fileName);
            if(stream != null){
                Reader reader = new InputStreamReader(stream, propertyFileEncoding);
                loadedProperties.load(reader);
            }
        }
        catch(IOException e){

        }
    }
}

