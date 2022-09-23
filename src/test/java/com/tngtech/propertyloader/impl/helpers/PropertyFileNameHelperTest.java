package com.tngtech.propertyloader.impl.helpers;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class PropertyFileNameHelperTest {

    private PropertyFileNameHelper propertyFileNameHelper = new PropertyFileNameHelper();

    @Test
    public void testGetFileNames() {
        List<String> baseNameList = asList("baseName1", "baseName2");
        List<String> suffixList = asList("suffix1", "suffix2");

        assertThat(propertyFileNameHelper.getFileNames(baseNameList, suffixList, "extension")).contains(
                "baseName1.suffix1.extension",
                "baseName1.suffix2.extension",
                "baseName2.suffix1.extension",
                "baseName2.suffix2.extension"
        );
    }
}
