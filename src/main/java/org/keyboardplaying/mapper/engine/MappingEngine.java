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
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.keyboardplaying.mapper.annotation.Metadata;
import org.keyboardplaying.mapper.annotation.Nested;
import org.keyboardplaying.mapper.exception.MappingException;

// XXX JAVADOC
/**
 * @author cyChop (http://keyboardplaying.org/)
 */
public class MappingEngine {

//    private ConverterProvider converterProvider = new ConverterProvider();

    public <T> T mapToBean(Map<String, String> metadata, Class<T> beanType) throws MappingException {
        try {
            return mapToBean(metadata, beanType.newInstance());
        } catch (InstantiationException e) {
            throw new MappingException("Could not instanciate a new bean for type " + beanType.getSimpleName() + ".", e);
        } catch (IllegalAccessException e) {
            throw new MappingException("Could not instanciate a new bean for type " + beanType.getSimpleName() + ".", e);
        }
    }

    public <T> T mapToBean(Map<String, String> metadata, T bean) throws MappingException {
        /* Control the validity of arguments. */
        if (bean == null) {
            throw new MappingException("The supplied bean was null.");
        } else if (metadata == null) {
            throw new MappingException("The supplied metadata was null.");
        }

        /* Go and parse. */
        try {
            performMappingToBean(metadata, bean);
        } catch (IllegalAccessException e) {
            throw new MappingException(e);
        } catch (InvocationTargetException e) {
            throw new MappingException(e);
        } catch (NoSuchMethodException e) {
            throw new MappingException("Problem, setter not found.", e);
        }

        return bean;
    }

    private <T> void performMappingToBean(Map<String, String> metadata, T bean) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, MappingException {
        final Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Nested.class)) {
                performMappingToInnerBean(metadata, bean, field);
            } else if (field.isAnnotationPresent(Metadata.class)) {
                performMappingToField(metadata, bean, field);
            }
        }
    }

    private <T> void performMappingToInnerBean(Map<String, String> metadata, T bean, Field field)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, MappingException {
        Object innerBean = PropertyUtils.getProperty(bean, field.getName());
        if (innerBean == null) {
            innerBean = mapToBean(metadata, field.getType());
        } else {
            innerBean = mapToBean(metadata, innerBean);
        }
        PropertyUtils.setProperty(bean, field.getName(), innerBean);
    }

    private <T> void performMappingToField(Map<String, String> metadata, T bean, Field field)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Metadata datumSetting = field.getAnnotation(Metadata.class);
        String datumName = datumSetting.value();
        if (metadata.containsKey(datumName)) {
//            Converter<?> converter =
            PropertyUtils.setProperty(bean, field.getName(), metadata.get(datumName));
        }
    }
}
