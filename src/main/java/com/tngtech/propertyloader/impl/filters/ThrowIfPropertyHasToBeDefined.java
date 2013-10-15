package com.tngtech.propertyloader.impl.filters;

import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderFilter;

import java.util.Map;
import java.util.Properties;

/**
 * Throws an exception if a key is still mapped to <HAS_TO_BE_DEFINED>.
 */
public class ThrowIfPropertyHasToBeDefined implements PropertyLoaderFilter {

    public static final String HAS_TO_BE_DEFINED = "<HAS_TO_BE_DEFINED>";

    @Override
    public void filter(Properties properties) {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            if (HAS_TO_BE_DEFINED.equals(value)) {
                StringBuilder sb = new StringBuilder();
                sb.append("\n" + "Configuration incomplete: property " + key +
                        " is still mapped to " + value);
                properties.remove(entry.getKey());
                try {
                    filter(properties);
                } catch (ThrowIfPropertyHasToBeDefinedException e) {
                    sb.append(e.getMessage());
                }
                throw new ThrowIfPropertyHasToBeDefinedException(sb.toString());
            }
        }
    }

}
