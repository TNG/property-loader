package com.tngtech.propertyloader.impl;

import java.util.Properties;


public interface PropertyLoaderFilter {

    public void filter(Properties properties);
}
