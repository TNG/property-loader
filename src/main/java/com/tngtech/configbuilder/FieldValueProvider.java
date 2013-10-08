package com.tngtech.configbuilder;

/**
 * This interface must be implemented by any classes that are specified in a <code>@ValueTransformer</code> annotation.
 * Classes implementing this interface are used to transform String values into arbitrary objects. As inner classes of the config class they must be declared <code>public static</code>
 * and specified for a field using the <code>@ValueTransformer(ClassImplementingFieldValueProvider.class)</code> annotation.
 * @param <T>
 */
public interface FieldValueProvider<T> {
    public T getValue(String optionValue);
}
