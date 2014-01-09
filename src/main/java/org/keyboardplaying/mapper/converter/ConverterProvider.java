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
package org.keyboardplaying.mapper.converter;

import java.io.IOException;
import java.util.Map;

import org.apache.xbean.finder.ResourceFinder;
import org.keyboardplaying.mapper.utils.ClassUtils;

/**
 * Provides the correct implementation of {@link Converter} to use based on the
 * type of the field to convert.
 * <p/>
 * <h1>Adding custom converters</h1>
 * It is possible to add custom converters. To do so, you need to create a
 * {@code META-INF/services/org.keyboardplaying.mapper.converter.Converter}
 * directory in your project.
 * <p/>
 * In this directory, you will need to add one file per converter. This file
 * will be named after the fully qualified type of field your converter handles,
 * and contain only one line which is the fully qualified class of your
 * converter. For example, in the case of the {@link StringConverter}, the file
 * is
 * {@code META-INF/services/org.keyboardplaying.mapper.converter.Converter/java.lang.String}
 * , and its content is:
 *
 * <pre>
 * org.keyboardplaying.mapper.converter.StringConverter
 * </pre>.
 *
 * @author cyChop (http://keyboardplaying.org/)
 */
public class ConverterProvider {

    /**
     * A list of all converter implementations, with the converted class name as
     * a key.
     */
    @SuppressWarnings("rawtypes")
    private Map<String, Class<? extends Converter>> implementations;

    /* Initialize the map */
    {
        reloadImplementations();
    }

    /**
     * Reloads converter implementations based on the content of the directory
     * {@code META-INF/services/org.keyboardplaying.mapper.converter.Converter}.
     */
    public final void reloadImplementations() {
        ResourceFinder finder = new ResourceFinder("META-INF/services/");
        try {
            implementations = finder.mapAvailableImplementations(Converter.class);
        } catch (IOException e) {
            throw new IllegalStateException("Converter configuration files could not be accessed.", e);
        }
    }

    /**
     * Fetches the appropriate {@link Converter} for the supplied class.
     *
     * @param klass
     *            the class to convert from or to
     * @return the {@link Converter} to use for conversion
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @SuppressWarnings("unchecked")
    public <T> Converter<T> getConverter(Class<?> klass) {

        Class<T> destination = (Class<T>) (klass.isPrimitive() ? ClassUtils.wrap(klass) : klass);
        Class<? extends Converter<T>> converterClass = (Class<? extends Converter<T>>) implementations.get(destination
                .getName());

        try {
            return converterClass == null ? null : converterClass.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(converterClass.getName()
                    + " could not be instanciated. Does it define a public no-arg constructor?", e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(converterClass.getName()
                    + " could not be instanciated. Does it define a public no-arg constructor?", e);
        }
    }
}
