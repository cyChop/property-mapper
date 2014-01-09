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
package org.keyboardplaying.mapper.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Provides utilities for class manipulation.
 * 
 * @author cyChop (http://keyboardplaying.org/)
 */
public class ClassUtils {

    /** A correspondance table between primitive and wrapper classes. */
    private static final Map<Class<?>, Class<?>> WRAPPER_CLASSES;

    private static final Map<Class<?>, Object> DEFAULTS;

    static {
        /* Initialize correspondance table. */
        Map<Class<?>, Class<?>> wrappers = new HashMap<Class<?>, Class<?>>();
        wrappers.put(byte.class, Byte.class);
        wrappers.put(short.class, Short.class);
        wrappers.put(int.class, Integer.class);
        wrappers.put(long.class, Long.class);
        wrappers.put(float.class, Float.class);
        wrappers.put(double.class, Double.class);
        wrappers.put(char.class, Character.class);
        wrappers.put(boolean.class, Boolean.class);
        // no use making it final if it is mutable
        WRAPPER_CLASSES = Collections.unmodifiableMap(wrappers);

        Map<Class<?>, Object> defaults = new HashMap<Class<?>, Object>();
        defaults.put(boolean.class, false);
        defaults.put(char.class, '\0');
        defaults.put(byte.class, (byte) 0);
        defaults.put(short.class, (short) 0);
        defaults.put(int.class, 0);
        defaults.put(long.class, 0L);
        defaults.put(float.class, 0f);
        defaults.put(double.class, 0d);
        DEFAULTS = Collections.unmodifiableMap(defaults);
    }

    /**
     * Returns the wrapper class of the supplied primitive class.
     * <p/>
     * If the supplied type is not primitive, it will be returned as is.
     * 
     * @param klass
     *            the primitive type to wrap
     * @return the wrapper class for the supplied primitive type
     */
    public static Class<?> wrap(Class<?> klass) {
        assert klass.isPrimitive();

        return WRAPPER_CLASSES.containsKey(klass) ? WRAPPER_CLASSES.get(klass) : klass;
    }

    /**
     * Returns the primitive type corresponding to the supplied wrapper class.
     * <p/>
     * If the supplied class is not a wrapper, it will be returned as is.
     * 
     * @param klass
     *            the wrapper class to unwrap
     * @return the primitive class for the supplied wrapper class
     */
    public static Class<?> unwrap(Class<?> klass) {
        assert !klass.isPrimitive();

        Class<?> result = klass;
        for (Entry<Class<?>, Class<?>> entry : WRAPPER_CLASSES.entrySet()) {
            if (entry.getValue().equals(klass)) {
                result = entry.getKey();
                break;
            }
        }
        return result;
    }

    /**
     * Returns the default value for the supplied class.
     * 
     * @param klass
     *            the type whose default value is wanted
     * @return the default value for the supplied class
     */
    public static Object getDefaultValue(Class<?> klass) {
        return DEFAULTS.get(klass);
    }
}
