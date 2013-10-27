package com.tngtech.propertyloader.impl.helpers;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class PropertyFileNameHelperTest {

    private PropertyFileNameHelper propertyFileNameHelper = new PropertyFileNameHelper();
    private String[] baseNames = {"baseName1", "baseName2"};
    private String[] suffixes = {"suffix1", "suffix2"};
    List<String> baseNameList = Arrays.asList(baseNames);
    List<String> suffixList = Arrays.asList(suffixes);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetFileNames() {
        assertTrue(propertyFileNameHelper.getFileNames(baseNameList, suffixList, "extension").contains("baseName1.suffix1.extension"));
        assertTrue(propertyFileNameHelper.getFileNames(baseNameList, suffixList, "extension").contains("baseName1.suffix2.extension"));
        assertTrue(propertyFileNameHelper.getFileNames(baseNameList, suffixList, "extension").contains("baseName2.suffix1.extension"));
        assertTrue(propertyFileNameHelper.getFileNames(baseNameList, suffixList, "extension").contains("baseName2.suffix2.extension"));
    }
}
