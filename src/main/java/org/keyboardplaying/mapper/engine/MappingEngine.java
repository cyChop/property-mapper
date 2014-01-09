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
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.keyboardplaying.mapper.annotation.DefaultValue;
import org.keyboardplaying.mapper.annotation.Metadata;
import org.keyboardplaying.mapper.annotation.Nested;
import org.keyboardplaying.mapper.exception.ConversionException;
import org.keyboardplaying.mapper.exception.MappingException;

// XXX JAVADOC
/**
 * The mapping engine for mapping a POJO to a flat map (mapping).
 * 
 * @author cyChop (http://keyboardplaying.org/)
 */
public class MappingEngine extends AbstractEngine {

    public <T> Map<String, String> map(T bean) throws MappingException {
        return map(bean, null);
    }

    public <T> Map<String, String> map(T bean, Map<String, String> map) throws MappingException {
        Map<String, String> result = map == null ? new HashMap<String, String>() : map;

        /* Control the validity of arguments. */
        if (bean == null) {
            throw new MappingException("The supplied bean was null.");
        }

        /* Go and parse. */
        final Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Nested.class)) {
                performInnerMapping(bean, field, result);
            } else if (field.isAnnotationPresent(Metadata.class)) {
                performFieldMapping(bean, field, result);
            }
        }

        return result;
    }

    private <T> void performInnerMapping(T bean, Field field, Map<String, String> result) throws MappingException {
        try {

            Object value = PropertyUtils.getProperty(bean, field.getName());
            if (value != null) {
                map(value, result);
            }

        } catch (IllegalArgumentException e) {
            throw new MappingException("Error while mapping nested " + field.getName() + " of "
                    + field.getDeclaringClass().getName(), e);
        } catch (IllegalAccessException e) {
            throw new MappingException("Error while mapping nested " + field.getName() + " of "
                    + field.getDeclaringClass().getName(), e);
        } catch (InvocationTargetException e) {
            throw new MappingException("Error while mapping nested " + field.getName() + " of "
                    + field.getDeclaringClass().getName(), e);
        } catch (NoSuchMethodException e) {
            throw new MappingException("Error while mapping nested " + field.getName() + " of "
                    + field.getDeclaringClass().getName(), e);
        }
    }

    private <T> void performFieldMapping(T bean, Field field, Map<String, String> map) throws MappingException {
        Metadata settings = field.getAnnotation(Metadata.class);

        /* Fetch the value from the bean. */
        String value = getFieldAsString(bean, field, settings);
        if (value == null) {

            /* Field is null. */

            if (field.isAnnotationPresent(DefaultValue.class)) {

                /* Use the default value instead. */
                value = field.getAnnotation(DefaultValue.class).value();

            } else if (settings.mandatory()) {

                /* Data is absent though mandatory, raise an exception. */
                throw new MappingException("Mandatory field " + field.getName() + " of "
                        + field.getDeclaringClass().getName() + " is null and does not define a default value.");
            }
        }
        System.out.println(settings.value() + ":" + value);
        map.put(settings.value(), value);
    }

    private <T> String getFieldAsString(T bean, Field field, Metadata settings) throws MappingException {
        String customGetter = settings.customGetter();

        String result;
        if (StringUtils.isEmpty(customGetter)) {
            result = getFieldAsString(bean, field);
        } else {
            // a custom getter was defined, overrides the default converter
            result = getFieldUsingCustomGetter(bean, customGetter);
        }
        return result;
    }

    private <T> String getFieldAsString(T bean, Field field) throws MappingException {
        try {

            Object value = PropertyUtils.getProperty(bean, field.getName());
            return value == null ? null : getConverter(field).convertToString(value);

        } catch (IllegalAccessException e) {
            throw new MappingException("Field " + field.getName() + " of " + field.getDeclaringClass().getName()
                    + " could not be got.", e);
        } catch (ConversionException e) {
            throw new MappingException("Field " + field.getName() + " of " + field.getDeclaringClass().getName()
                    + " could not be got.", e);
        } catch (InvocationTargetException e) {
            throw new MappingException("Field " + field.getName() + " of " + field.getDeclaringClass().getName()
                    + " could not be got.", e);
        } catch (NoSuchMethodException e) {
            throw new MappingException("Field " + field.getName() + " of " + field.getDeclaringClass().getName()
                    + " could not be got.", e);
        }
    }

    private <T> String getFieldUsingCustomGetter(T bean, String customGetter) throws MappingException {
        try {

            Method method = bean.getClass().getMethod(customGetter);
            return (String) method.invoke(bean);

        } catch (IllegalArgumentException e) {
            throw new MappingException("Custom getter " + customGetter + " of " + bean.getClass().getName()
                    + " could not be called.", e);
        } catch (IllegalAccessException e) {
            throw new MappingException("Custom getter " + customGetter + " of " + bean.getClass().getName()
                    + " could not be called.", e);
        } catch (InvocationTargetException e) {
            throw new MappingException("Custom getter " + customGetter + " of " + bean.getClass().getName()
                    + " could not be called.", e);
        } catch (SecurityException e) {
            throw new MappingException("Custom getter " + customGetter + " of " + bean.getClass().getName()
                    + " could not be called.", e);
        } catch (NoSuchMethodException e) {
            throw new MappingException("Custom getter " + customGetter + " of " + bean.getClass().getName()
                    + " could not be called.", e);
        } catch (ClassCastException e) {
            throw new MappingException("Custom getter " + customGetter + " of " + bean.getClass().getName()
                    + " did not return a String", e);
        }
    }
}
