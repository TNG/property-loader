package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderOpener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class PropertyFileReader {

    private static final Logger log = LoggerFactory.getLogger(PropertyFileReader.class);

    private final PropertyLoaderFactory propertyLoaderFactory;

    public PropertyFileReader(PropertyLoaderFactory propertyLoaderFactory) {
        this.propertyLoaderFactory = propertyLoaderFactory;
    }

    public Properties tryToReadPropertiesFromFile(String fileName, String propertyFileEncoding, PropertyLoaderOpener opener) {
        Properties newProperties;

        InputStream stream = opener.open(fileName);

        if (stream == null) {
            log.debug("file {} not found {}", fileName, opener);
            newProperties = propertyLoaderFactory.getEmptyProperties();
        } else {
            log.info("file {} found for reading {} with encoding {}", fileName, opener, propertyFileEncoding);
            if (fileName.toLowerCase().endsWith("xml")) {
                log.debug("attempting to find and read xml file {} {}", fileName, opener);
                try {
                    newProperties = readFromXML(stream);
                } catch (InvalidPropertiesFormatException e) {
                    throw new PropertyFileReaderException(String.format("error reading properties from from '%s': this xml file is not a valid properties format", fileName), e);
                } catch (IOException e) {
                    throw new PropertyFileReaderException(String.format("error reading properties from stream created from '%s' in opener '%s'", fileName, opener), e);
                }
            } else {
                log.debug("attempting to find and read properties file {} with encoding {} {}", fileName, propertyFileEncoding, opener);
                try {
                    newProperties = read(stream, propertyFileEncoding);
                } catch (IOException e) {
                    throw new PropertyFileReaderException(String.format("error reading properties from stream created from '%s' with encoding '%s' in opener '%s'", fileName, propertyFileEncoding, opener), e);
                }
            }
        }
        return newProperties;
    }

    private Properties read(InputStream stream, String propertyFileEncoding) throws IOException {
        Properties loadedProperties = propertyLoaderFactory.getEmptyProperties();
        Reader reader = propertyLoaderFactory.getInputStreamReader(stream, propertyFileEncoding);
        loadedProperties.load(reader);

        return loadedProperties;
    }

    private Properties readFromXML(InputStream stream) throws IOException {
        Properties loadedProperties = propertyLoaderFactory.getEmptyProperties();
        loadedProperties.loadFromXML(stream);
        return loadedProperties;
    }
}
