package com.tngtech.propertyloader.impl;

import java.util.ArrayList;
import java.util.List;

public class SuffixConfig {

    public List<String> getFileNames(List<String> baseNames, String fileExtension)
    {
        List<String> fileNameList = new ArrayList<String>();
        for (String baseName : baseNames)
        {
            fileNameList.add(baseName + "." + fileExtension);
            for (String suffix : this.getSuffixes())
            {
                fileNameList.add(baseName + "." + suffix + "." + fileExtension);
            }
        }
        return fileNameList;
    }

    public List<String> getSuffixes()
    {
        return new ArrayList<String>();
    }
}
