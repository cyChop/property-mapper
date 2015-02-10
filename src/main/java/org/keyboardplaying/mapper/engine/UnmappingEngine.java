/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keyboardplaying.mapper.engine;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.keyboardplaying.mapper.annotation.DefaultValue;
import org.keyboardplaying.mapper.annotation.Metadata;
import org.keyboardplaying.mapper.annotation.Nested;
import org.keyboardplaying.mapper.exception.ConversionException;
import org.keyboardplaying.mapper.exception.ConverterInitializationException;
import org.keyboardplaying.mapper.exception.MappingException;

/**
 * The mapping engine for mapping a flat map to a POJO (unmapping).
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class UnmappingEngine extends AbstractEngine {

    /** A map containing the default values of objects based on their types. */
    private static final Map<Class<?>, Object> DEFAULT_VALUES;

    static {
        /*
         * Initialize default values for primitive types.
         * 
         * Skip Objects: their default value is null anyway.
         */
        @SuppressWarnings("serial")
        Map<Class<?>, Object> defaultValues = new HashMap<Class<?>, Object>() {
            /* Default primitive values (called through reflection). */
            @SuppressWarnings("unused")
            private boolean b;
            @SuppressWarnings("unused")
            private byte by;
            @SuppressWarnings("unused")
            private char c;
            @SuppressWarnings("unused")
            private double d;
            @SuppressWarnings("unused")
            private float f;
            @SuppressWarnings("unused")
            private int i;
            @SuppressWarnings("unused")
            private long l;
            @SuppressWarnings("unused")
            private short s;

            {
                for (final Field field : getClass().getDeclaredFields()) {
                    try {
                        put(field.getType(), field.get(this));
                    } catch (IllegalAccessException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            }
        };

        DEFAULT_VALUES = Collections.unmodifiableMap(defaultValues);
    }

    /**
     * Instantiates a new bean of specified type and unmaps metadata to it, based on the annotations
     * in the bean.
     *
     * @param metadata
     *            the flat metadata
     * @param beanType
     *            the destination bean's type
     * @return the destination bean
     * @throws MappingException
     *             when the mapping fails
     */
    public <T> T unmap(Map<String, String> metadata, Class<T> beanType) throws MappingException {
        try {
            return unmap(metadata, beanType.newInstance());
        } catch (InstantiationException e) {
            throw new MappingException("Could not instanciate a new bean for type "
                    + beanType.getSimpleName() + ".", e);
        } catch (IllegalAccessException e) {
            throw new MappingException("Could not instanciate a new bean for type "
                    + beanType.getSimpleName() + ".", e);
        }
    }

    /**
     * Unmaps metadata to a destination bean, based on the annotations in the bean.
     * <p/>
     * Non-annotated fields are not overwritten.
     *
     * @param metadata
     *            the flat metadata
     * @param bean
     *            the destination bean
     * @return the destination bean
     * @throws MappingException
     *             when the mapping fails
     */
    public <T> T unmap(Map<String, String> metadata, T bean) throws MappingException {
        /* Control the validity of arguments. */
        if (bean == null) {
            throw new MappingException("The supplied bean was null.");
        } else if (metadata == null) {
            throw new MappingException("The supplied metadata was null.");
        }

        /* Now perform the unmapping. */
        final Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Nested.class)) {
                performInnerUnmapping(metadata, bean, field);
            } else if (field.isAnnotationPresent(Metadata.class)) {
                performFieldUnmapping(metadata, bean, field);
            }
        }

        return bean;
    }

    /**
     * Performs the unmapping to a field which is an inner bean (marked with the {@link Nested}
     * annotation).
     *
     * @param metadata
     *            the flat metadata
     * @param bean
     *            the destination bean
     * @param field
     *            the field to set
     * @throws MappingException
     *             when the mapping fails
     */
    private <T> void performInnerUnmapping(Map<String, String> metadata, T bean, Field field)
            throws MappingException {
        try {

            Object innerBean = PropertyUtils.getProperty(bean, field.getName());
            if (innerBean == null) {
                String className = field.getAnnotation(Nested.class).className();
                try {
                    innerBean = unmap(
                            metadata,
                            className == null || className.length() == 0 ? field.getType() : Class
                                    .forName(className));
                } catch (ClassNotFoundException e) {
                    throw new MappingException("Could not find class " + className
                            + " when instantiating bean for inner field " + field.getName()
                            + " of class " + field.getDeclaringClass().getName(), e);
                }
            } else {
                innerBean = unmap(metadata, innerBean);
            }
            PropertyUtils.setProperty(bean, field.getName(), innerBean);

        } catch (IllegalAccessException e) {
            throw new MappingException("Error while unmapping nested bean " + field.getName()
                    + " of " + field.getDeclaringClass().getName(), e);
        } catch (InvocationTargetException e) {
            throw new MappingException("Error while unmapping nested bean " + field.getName()
                    + " of " + field.getDeclaringClass().getName(), e);
        } catch (NoSuchMethodException e) {
            throw new MappingException("Error while unmapping nested bean " + field.getName()
                    + " of " + field.getDeclaringClass().getName(), e);
        }
    }

    /**
     * Performs the unmapping for a convertible-typed field.
     *
     * @param metadata
     *            the flat metadata
     * @param bean
     *            the destination bean
     * @param field
     *            the field to set
     * @throws MappingException
     *             when the mapping fails
     */
    private <T> void performFieldUnmapping(Map<String, String> metadata, T bean, Field field)
            throws MappingException {
        Metadata settings = field.getAnnotation(Metadata.class);
        String metadataName = settings.value();

        if (metadata.containsKey(metadataName)) {

            /* Set the value using the value provided with the metadata. */
            setField(metadata, bean, field, settings, metadata.get(metadataName));

        } else if (settings.mandatory()) {

            /* Data is absent though mandatory, raise an exception. */
            throw new MappingException("Mandatory data " + metadataName
                    + " is missing from metadata map (" + metadata.keySet().toString() + ").");

        } else if (field.isAnnotationPresent(DefaultValue.class)) {

            /* Set the value using the provided default value. */
            setField(bean, field, field.getAnnotation(DefaultValue.class).value());
        }
        /* Otherwise, leave field as is. */
    }

    /**
     * Sets the value of the supplied field in the supplied bean to the supplied value, in
     * accordance with the settings provided via annotations.
     *
     * @param metadata
     *            the flat metadata
     * @param bean
     *            the destination bean
     * @param field
     *            the field to set
     * @param settings
     *            the main settings for parsing the value from metadata
     * @param value
     *            the value which should be set
     * @throws MappingException
     *             when the mapping fails
     */
    private <T> void setField(Map<String, String> metadata, T bean, Field field, Metadata settings,
            String value) throws MappingException {
        String customSetter = settings.customSetter();
        if (customSetter == null || customSetter.length() == 0) {
            setField(bean, field, value);
        } else {
            // a custom setter was defined, overrides the default converter
            setFieldUsingCustomSetter(bean, customSetter, value, metadata);
        }
    }

    /**
     * Sets the field, using the default converter for the type of the field and the field's setter.
     * <p/>
     * This requires the bean to respect the bean notation.
     *
     * @param bean
     *            the destination bean
     * @param field
     *            the field to set
     * @param value
     *            the non-converted value for the field
     * @throws MappingException
     *             when the mapping fails
     */
    private <T> void setField(T bean, Field field, String value) throws MappingException {
        try {
            PropertyUtils.setProperty(
                    bean,
                    field.getName(),
                    value == null ? DEFAULT_VALUES.get(field.getType()) : this.<T> getConverter(
                            field).convertFromString(value));
        } catch (ConverterInitializationException e) {
            throw new MappingException("Converter for field " + field.getName() + " of "
                    + field.getDeclaringClass().getName() + " could not be initialized.", e);
        } catch (IllegalAccessException e) {
            throw new MappingException("Field " + field.getName() + " of "
                    + field.getDeclaringClass().getName() + " could not be set.", e);
        } catch (InvocationTargetException e) {
            throw new MappingException("Field " + field.getName() + " of "
                    + field.getDeclaringClass().getName() + " could not be set.", e);
        } catch (NoSuchMethodException e) {
            throw new MappingException("Field " + field.getName() + " of "
                    + field.getDeclaringClass().getName() + " could not be set.", e);
        } catch (ConversionException e) {
            throw new MappingException("Field " + field.getName() + " of "
                    + field.getDeclaringClass().getName() + " could not be set.", e);
        }
    }

    /**
     * Sets the field, using the supplied setter.
     * <p/>
     * The signature for the setter should be
     * {@code setSomething(java.lang.String, java.util.Map<String, String>)}. It will receive the
     * value for the required metadata as the first argument, and the whole flat metadata as the
     * second.
     *
     * @param bean
     *            the destination bean
     * @param customSetter
     *            the setter to use
     * @param value
     *            the non-converted value for the field
     * @param metadata
     *            the flat metadata
     *
     * @throws MappingException
     *             when the mapping fails
     */
    private <T> void setFieldUsingCustomSetter(T bean, String customSetter, String value,
            Map<String, String> metadata) throws MappingException {
        try {

            Method method = bean.getClass().getMethod(customSetter, String.class, Map.class);
            method.invoke(bean, value, metadata);

        } catch (IllegalArgumentException e) {
            throw new MappingException("Custom setter " + customSetter + " of "
                    + bean.getClass().getName() + " could not be called.", e);
        } catch (IllegalAccessException e) {
            throw new MappingException("Custom setter " + customSetter + " of "
                    + bean.getClass().getName() + " could not be called.", e);
        } catch (InvocationTargetException e) {
            throw new MappingException("Custom setter " + customSetter + " of "
                    + bean.getClass().getName() + " could not be called.", e);
        } catch (SecurityException e) {
            throw new MappingException("Custom setter " + customSetter + " of "
                    + bean.getClass().getName() + " could not be called.", e);
        } catch (NoSuchMethodException e) {
            throw new MappingException("Custom setter " + customSetter + " of "
                    + bean.getClass().getName() + " could not be called.", e);
        }
    }
}
