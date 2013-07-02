package com.tngtech.config;

public abstract class ConfigBase {
    /**
     * The application config file names start with this prefix
     */
    public static final String APP_CONFIG_BASENAME = "TNGTECH";

    /**
     * The package name of the project
     */
    public static final String APP_PACKAGE_PREFIX = "com.tngtech";

    /**
     * Email address that the code can safely send emails to, hopefully without upsetting anyone.
     */
    public static final String SAFE_EMAIL_ADDRESS = "siemens-sen-ccs@tngtech.com";

}
