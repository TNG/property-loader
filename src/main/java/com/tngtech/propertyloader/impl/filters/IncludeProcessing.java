package com.tngtech.propertyloader.impl.filters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tngtech.infrastructure.startupshutdown.SystemLog;
import com.tngtech.infrastructure.util.OrderedProperties;
import com.tngtech.propertyloader.impl.PropertyLoaderFilter;
import com.tngtech.propertyloader.impl.PropertyLoaderState;

class IncludeProcessing implements PropertyLoaderFilter {
    private static final String INCLUDE_KEY = "%%include%%";

    @Override
    public void filter(OrderedProperties props, PropertyLoaderState state) {
        List<String> includes = new ArrayList<String>();
        collectIncludes(props, includes);

        while (includes.size() > 0) {
            List<String> subIncludes = new ArrayList<String>();

            for (String include : includes) {
                OrderedProperties additionalProps = state.getLoader().rawOrderedPropertiesWithBaseName(include, state.getSuffix());
                collectIncludes(additionalProps, subIncludes);
                props.addAll(additionalProps);

                if (additionalProps.size() == 0) {
                    SystemLog.warn("PropertyLoader: Include for '" + include + "' resulted in nothing loaded.");
                }
            }

            includes = subIncludes;
        }
    }

    private void collectIncludes(OrderedProperties props, List<String> includes) {
        for (Map.Entry<String, String> entry : props.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if ("no".equals(value)) {
                continue;
            }
            if (!key.startsWith(INCLUDE_KEY)) {
                continue;
            }

            includes.add(key.substring(INCLUDE_KEY.length()));
        }
    }
}
