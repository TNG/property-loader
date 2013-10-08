package com.tngtech.configbuilder;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainTest {
    public static void main(String[] args){
        Locale.setDefault(Locale.ITALIAN);
        ResourceBundle bundle = ResourceBundle.getBundle("errors");
        System.out.println(bundle.getString("standardMessage"));
    }
}
