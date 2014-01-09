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

import static org.junit.Assert.assertEquals;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * @author cyChop (http://keyboardplaying.org/)
 */
public class ClassUtilsTest {

    private static Log log = LogFactory.getLog(ClassUtilsTest.class);

    @Test
    public void testWrap() {
        assertEquals(Byte.class, ClassUtils.wrap(byte.class));
        assertEquals(Short.class, ClassUtils.wrap(short.class));
        assertEquals(Integer.class, ClassUtils.wrap(int.class));
        assertEquals(Long.class, ClassUtils.wrap(long.class));
        assertEquals(Float.class, ClassUtils.wrap(float.class));
        assertEquals(Double.class, ClassUtils.wrap(double.class));
        assertEquals(Character.class, ClassUtils.wrap(char.class));
        assertEquals(Boolean.class, ClassUtils.wrap(boolean.class));
        try {
            assertEquals(String.class, ClassUtils.wrap(String.class));
        } catch (AssertionError e) {
            log.info("Assertions enabled, skipping error. This should not happen in production mode.");
            log.debug(e.getMessage(), e);
        }
    }

    @Test
    public void testUnwrap() {
        assertEquals(byte.class, ClassUtils.unwrap(Byte.class));
        assertEquals(short.class, ClassUtils.unwrap(Short.class));
        assertEquals(int.class, ClassUtils.unwrap(Integer.class));
        assertEquals(long.class, ClassUtils.unwrap(Long.class));
        assertEquals(float.class, ClassUtils.unwrap(Float.class));
        assertEquals(double.class, ClassUtils.unwrap(Double.class));
        assertEquals(char.class, ClassUtils.unwrap(Character.class));
        assertEquals(boolean.class, ClassUtils.unwrap(Boolean.class));
        assertEquals(String.class, ClassUtils.unwrap(String.class));
        try {
            assertEquals(int.class, ClassUtils.unwrap(int.class));
        } catch (AssertionError e) {
            log.info("Assertions enabled, skipping error. This should not happen in production mode.");
            log.debug(e.getMessage(), e);
        }
    }
}
