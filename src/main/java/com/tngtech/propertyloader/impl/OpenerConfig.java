package com.tngtech.propertyloader.impl;

import java.util.List;

public class OpenerConfig {

    public OpenerConfig(){

    }

    private List<PropertyLoaderOpener> openers;

    public List<PropertyLoaderOpener> getOpeners()
    {
        return openers;
    }

    public void setOpeners(List<PropertyLoaderOpener> openers) {
        this.openers = openers;
    }
}
