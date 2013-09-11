package com.tngtech.propertyloader.impl.helpers;


import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PropertyFileNameHelper {
    public List<String> getFileNames(List<String> baseNames, List<String> suffixes, String fileExtension)
    {
        List<String> fileNameList = Lists.newArrayList();
        for (String baseName : baseNames)
        {
            fileNameList.add(baseName + "." + fileExtension);
            for (String suffix : suffixes)
            {
                fileNameList.add(baseName + "." + suffix + "." + fileExtension);
            }
        }
        return fileNameList;
    }
}
