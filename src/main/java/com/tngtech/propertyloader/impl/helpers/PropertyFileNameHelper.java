package com.tngtech.propertyloader.impl.helpers;


import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class PropertyFileNameHelper {
    public List<String> getFileNames(Collection<String> baseNames, Collection<String> suffixes, String fileExtension)
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
