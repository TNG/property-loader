package com.tngtech.propertyloader.impl.helpers;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PropertyFileNameHelper {
    public List<String> getFileNames(Collection<String> baseNames, Collection<String> suffixes, String fileExtension) {
        List<String> fileNameList = new ArrayList<String>();
        for (String baseName : baseNames) {
            fileNameList.add(baseName + "." + fileExtension);
            for (String suffix : suffixes) {
                fileNameList.add(baseName + "." + suffix + "." + fileExtension);
            }
        }
        return fileNameList;
    }
}
