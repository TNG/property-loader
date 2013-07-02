package com.tngtech.propertyloader;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.tngtech.config.ConfigBase;
import com.tngtech.propertyloader.impl.SuffixConfig;
import com.tngtech.propertyloader.impl.filters.FilterConfig;
import com.tngtech.propertyloader.impl.openers.OpenerConfig;

/**
 * Utility class for shorthand access to PropertyLoader
 */
public abstract class PropertyUtil {
    // private cache if the properties are read in multiple times, speeds up the initial configuration loading
    private static Map<String, Properties> cachedProperties = Collections.synchronizedMap(new HashMap<String, Properties>());
    private static Properties defaultProperties;

    private PropertyUtil() {
        // cannot be instantiated
    }

    /**
     * Returns a new PropertyLoader configured with the default settings of CCSWEB.
     * <br>
     * Property loading is additive, with later files overriding settings in earlier files.
     * <br>
     * In the default config, the following file names are searched for basename "foo", extension "lcf":
     * <ul>
     * <li> foo.lcf </li>
     * <li> foo.$hostname.lcf </li>
     * <li> foo.$USER.lcf </li>
     * <li> foo.override.lcf </li>
     * </ul>
     * <p/>
     * These files are searched for in the following locations:
     * <ul>
     * <li> classpath </li>
     * <li> current directory </li>
     * <li> user HOME directory </li>
     * </ul>
     * <p/>
     * The rules are: Later filenames override earlier filenames, within one filename later locations override earlier ones.
     * <p/>
     * So for example, a setting in "foo.lcf" in the current directory is overridden by a setting in "foo.karsten.lcf"
     * in the classpath, which is overridden by "foo.karsten.lcf" in my home directory, which is overridden
     * in turn by "foo.override.lcf" in the classpath.
     */
    public static PropertyLoader defaultConfig() {
        PropertyLoader result = new PropertyLoader();

        result.getOpeners().addAll(OpenerConfig.defaultConfig());
        result.getFilters().addAll(FilterConfig.defaultConfig());
        result.getSuffixes().addAll(SuffixConfig.defaultConfig());

        return result;
    }

    /**
     * Shorthand to load the main application config properties
     */
    public static Properties appConfig() {
        synchronized (PropertyUtil.class) {
            if (defaultProperties == null) {
                defaultProperties = defaultConfig().load("appconfig/" + ConfigBase.APP_CONFIG_BASENAME + "Application", "properties");
            }
        }
        return defaultProperties;
    }

    public static Properties appConfig(String... baseNames) {
        synchronized (PropertyUtil.class) {
            Properties result = new Properties();

            if (defaultProperties == null) {
                for (String baseName : baseNames) {
                    result.putAll(defaultConfig().load("appconfig/" + baseName, "properties"));
                }

                defaultProperties = result;
            }
        }
        return defaultProperties;
    }

    /**
     * Loads a fileset with a default PropertyLoader.
     * <p/>
     * Remembers previous invocations with the same parameters,
     * and returns the result from the previous call instead of loading the fileset again.
     */
    public static Properties cachedLoad(String basename, String appendix) {
        String key = basename;
        if (appendix != null) {
            key = key + "." + appendix;
        }

        Properties props = cachedProperties.get(key);

        if (props == null) {
            props = defaultConfig().load(basename, appendix);
            cachedProperties.put(key, props);
        }

        return props;
    }
}
