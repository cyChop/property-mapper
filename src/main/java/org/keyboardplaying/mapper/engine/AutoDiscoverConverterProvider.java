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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.keyboardplaying.mapper.converter.Converter;
import org.keyboardplaying.mapper.exception.ConverterInitializationException;

/**
 * Provides the correct implementation of {@link Converter} to use based on the type of the field to
 * convert.
 * <p/>
 * <h1>Adding custom converters</h1> It is possible to add custom converters. To do so, you need to
 * create a {@code META-INF/services/org.keyboardplaying.mapper.converter} directory in your
 * project.
 * <p/>
 * In this directory, you will need to add one descriptor file per converter. This file will be
 * named after the fully qualified type of field your converter handles, and contain only one line
 * which is the fully qualified class of your converter. For example, in the case of the
 * {@link org.keyboardplaying.mapper.converter.StringConverter}, the file is:
 *
 * <pre>
 * META-INF/services/org.keyboardplaying.mapper.converter/java.lang.String
 * </pre>
 *
 * and its content is:
 *
 * <pre>
 * converter=org.keyboardplaying.mapper.converter.StringConverter
 * </pre>
 * <p/>
 * This provider instantiates the converters only when required and then return them as singletons.
 * <p/>
 * This class implements the singleton design pattern.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class AutoDiscoverConverterProvider implements ConverterProvider {

    private static final String CONVERTER_DEFINITION_PATH = "META-INF/services/org.keyboardplaying.mapper.converter/";
    private static final String CONVERTER_PROPERTY = "converter";

    private static AutoDiscoverConverterProvider instance = new AutoDiscoverConverterProvider();

    /** A list of converter types to use based on the field type. */
    private Map<Class<?>, Class<? extends Converter<?>>> converterDefinitions = new HashMap<>();
    /** A list of all previously loaded converters based on their type. */
    private Map<Class<? extends Converter<?>>, Converter<?>> converters = new HashMap<>();

    /**
     * Returns the single instance of this class.
     *
     * @return the single instance of this class
     */
    public static AutoDiscoverConverterProvider getInstance() {
        return instance;
    }

    /* Private constructor. */
    private AutoDiscoverConverterProvider() {
    }

    /**
     * Fetches the appropriate {@link Converter} for the supplied class.
     *
     * @param klass
     *            the class to convert from or to
     * @return the {@link Converter} to use for conversion
     * @throws ConverterInitializationException
     *             if the {@link Converter} cannot be found or initialized
     */
    @Override
    public <T> Converter<? super T> getConverter(Class<T> klass)
            throws ConverterInitializationException {
        Objects.requireNonNull(klass, "A class must be provided when requiring a converter.");

        @SuppressWarnings("unchecked")
        Class<? extends Converter<? super T>> converterClass = (Class<? extends Converter<? super T>>) converterDefinitions
                .get(klass);
        if (converterClass == null) {
            converterClass = getConverterClass(klass);
            converterDefinitions.put(klass, converterClass);
        }

        @SuppressWarnings("unchecked")
        Converter<? super T> converter = (Converter<? super T>) converters.get(converterClass);
        if (converter == null) {
            try {
                converter = converterClass.newInstance();
                converters.put(converterClass, converter);
            } catch (InstantiationException e) {
                throw new ConverterInitializationException(
                        converterClass.getName()
                                + " could not be instanciated. Does it define a public no-arg constructor?",
                        e);
            } catch (IllegalAccessException e) {
                throw new ConverterInitializationException(
                        converterClass.getName()
                                + " could not be instanciated. Does it define a public no-arg constructor?",
                        e);
            }
        }
        return converter;
    }

    /**
     * Returns the class of the converter to use for a specific class.
     *
     * @param klass
     *            the class to look a converter for
     * @return the class for the converter to use
     * @throws ConverterInitializationException
     *             if the {@link Converter} cannot be found or initialized
     */
    @SuppressWarnings("unchecked")
    private <T> Class<? extends Converter<? super T>> getConverterClass(Class<T> klass)
            throws ConverterInitializationException {
        String uri = CONVERTER_DEFINITION_PATH + klass.getName();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = getClass().getClassLoader();
        }

        InputStream in = classLoader.getResourceAsStream(uri);
        if (in == null) {
            in = AutoDiscoverConverterProvider.class.getClassLoader().getResourceAsStream(uri);
            if (in == null) {
                throw new ConverterInitializationException(
                        "No converter descriptor found for type " + klass.getName() + ".");
            }
        }

        String converterClassName;
        try (InputStream src = in) {
            Properties properties = new Properties();
            properties.load(in);

            converterClassName = properties.getProperty(CONVERTER_PROPERTY);
            if (converterClassName == null) {
                throw new ConverterInitializationException("Converter descriptor for class "
                        + klass.getName() + " is incorrect (empty).");
            }
        } catch (IOException e) {
            throw new ConverterInitializationException(
                    "Error occurred when trying to read converter descriptor for type "
                            + klass.getName(), e);
        }

        try {
            Class<?> converterClass = Class.forName(converterClassName);
            if (!Converter.class.isAssignableFrom(converterClass)) {
                throw new ConverterInitializationException("Class " + converterClassName
                        + " for conversion of type " + klass.getName() + " does not extend "
                        + Converter.class.getName());
            }
            return (Class<? extends Converter<? super T>>) converterClass;
        } catch (ClassNotFoundException e) {
            throw new ConverterInitializationException("Class " + converterClassName
                    + " could not be found for conversion of type " + klass.getName() + ".");
        }
    }
}
