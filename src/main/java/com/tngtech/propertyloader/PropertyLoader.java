package com.tngtech.propertyloader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tngtech.infrastructure.exception.Reject;
import com.tngtech.infrastructure.startupshutdown.SystemLog;
import com.tngtech.infrastructure.util.OrderedProperties;
import com.tngtech.propertyloader.impl.PropertyLoaderFilter;
import com.tngtech.propertyloader.impl.PropertyLoaderOpener;
import com.tngtech.propertyloader.impl.PropertyLoaderState;

/**
 * Implements loading of files with .properties-like syntax, with bells and whistles.
 * <p/>
 * To obtain a configured instance, use {@link PropertyUtil#defaultConfig()}.
 * <p/>
 * To see which search paths are currently implemented, see the impl.openers subpackage.
 * To see which postprocessing steps are implemented, see the impl.filters subpackage.
 */
public class PropertyLoader {
    private static final String PROPERTY_FILE_ENCODING = "ISO-8859-1";
    private static final boolean DEFAULT_DEBUG =
            Boolean.parseBoolean(System.getProperty(PropertyLoader.class.getName() + ".debug", "false"));

    private boolean debug = DEFAULT_DEBUG;

    private List<String> baseNames = new ArrayList<String>();
    private String fileExtension;

    private List<String> suffixes = new ArrayList<String>();
    private List<PropertyLoaderOpener> openers = new ArrayList<PropertyLoaderOpener>();
    private List<PropertyLoaderFilter> filters = new ArrayList<PropertyLoaderFilter>();
    private Collection<String> loadedResources = null;

    public List<String> getSuffixes() {
        return suffixes;
    }

    public void setSuffixes(List<String> suffixes) {
        this.suffixes = suffixes;
    }

    public List<PropertyLoaderOpener> getOpeners() {
        return openers;
    }

    public void setOpeners(List<PropertyLoaderOpener> openers) {
        this.openers = openers;
    }

    public List<PropertyLoaderFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<PropertyLoaderFilter> filters) {
        this.filters = filters;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getExtension() {
        return fileExtension;
    }

    public void setExtension(String extension) {
        this.fileExtension = extension;
    }

    public void trackLoadedResources(Collection<String> destination) {
        this.loadedResources = destination;
    }

    public void addBaseName(String baseName) {
        baseNames.add(baseName);
    }

    public Properties load() {
        Reject.ifEmpty("no base names defined", baseNames);

        OrderedProperties orderedResult = new OrderedProperties();

        for (String baseName : baseNames) {

            SystemLog.info("PropertyLoader: --- loading for " + baseName + " with extension " + fileExtension + " started ---");
            OrderedProperties result = rawOrderedPropertiesWithBaseName(baseName, fileExtension);
            SystemLog.info("PropertyLoader: --- loading for " + baseName + " with extension " + fileExtension + " completed ---");

            orderedResult.addAll(result);
        }

        filterProperties("", fileExtension, orderedResult);

        return orderedResult.asProperties();
    }

    public Properties load(String baseName, String extension) {
        addBaseName(baseName);
        fileExtension = extension;

        return load();
    }

    private void filterProperties(String baseName, String extension, OrderedProperties result) {
        PropertyLoaderState state = new PropertyLoaderState(baseName, extension, this);
        for (PropertyLoaderFilter filter : filters) {
            filter.filter(result, state);
            debug("PropertyLoader: After " + filter, result);
        }
    }

    public OrderedProperties rawOrderedPropertiesWithBaseName(String basename, String extension) {
        OrderedProperties result = new OrderedProperties();

        loadFile(basename + "." + extension, result);
        for (String suffix : suffixes) {
            loadFile(basename + "." + suffix + "." + extension, result);
        }
        debug("PropertyLoader: After raw loading", result);
        return result;
    }

    private void loadFile(String filename, OrderedProperties target) {
        for (PropertyLoaderOpener loader : openers) {
            try {
                loadFileInLoader(filename, loader, target);
            } catch (IOException e) {
                SystemLog.warn("PropertyLoader: - error reading " + filename + " in " + loader, e);
            }
        }
    }

    private void loadFileInLoader(String filename, PropertyLoaderOpener loader, OrderedProperties target) throws IOException {
        InputStream stream = loader.open(filename);
        if (stream == null) {
            if (debug) {
                SystemLog.info("PropertyLoader: - tried " + filename + " in " + loader);
            }
            return;
        }

        if (loadedResources != null) {
            loadedResources.add(filename + " in " + loader);
        }

        SystemLog.info("PropertyLoader: - reading " + filename + " in " + loader);

        try {
            target.load(stream, PROPERTY_FILE_ENCODING);
        } finally {
            stream.close();
        }
    }

    private void debug(String message, OrderedProperties result) {
        if (!debug) {
            return;
        }
        SystemLog.debug(message);
        for (Map.Entry<String, String> entry : result.entrySet()) {
            SystemLog.debug("  " + entry.getKey() + "=" + entry.getValue());
        }
    }
}
