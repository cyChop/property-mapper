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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.keyboardplaying.mapper.exception.ConverterInitializationException;
import org.keyboardplaying.mapper.mock.converter.DoubleConverter;

// XXX Javadoc
/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class AutoDiscoverConverterProviderTest {

    private AutoDiscoverConverterProvider provider;

    @Before
    public void initProvider() {
        provider = AutoDiscoverConverterProvider.getInstance();
    }

    @Test
    public void testGetConverter() throws ConverterInitializationException {
        assertConverterClass(StringConverter.class, String.class);
        assertConverterClass(BooleanConverter.class, Boolean.class);
        assertConverterClass(BooleanConverter.class, boolean.class);
        assertConverterClass(IntegerConverter.class, Integer.class);
        assertConverterClass(IntegerConverter.class, int.class);
        assertConverterClass(LongConverter.class, Long.class);
        assertConverterClass(LongConverter.class, long.class);
        assertConverterClass(BigIntegerConverter.class, BigInteger.class);
        // assertConverterClass(DateConverter.class, Date.class);
        assertConverterClass(CalendarConverter.class, Calendar.class);
    }

    @Test
    public void testGetSubclassConverter() throws ConverterInitializationException {
        // you still need to write a descriptor for GregorianCalendar, though
        assertConverterClass(CalendarConverter.class, GregorianCalendar.class);
    }

    @Test(expected = ConverterInitializationException.class)
    public void testGetConverterWithoutDescriptor() throws ConverterInitializationException {
        // tests at the same time that auto-boxing is of no avail
        // (a bogus java.lang.Character descriptor exists)
        provider.getConverter(char.class);
    }

    @Test(expected = ConverterInitializationException.class)
    public void testGetConverterWithEmptyDescriptor() throws ConverterInitializationException {
        provider.getConverter(Short.class);
    }

    @Test(expected = InstantiationException.class)
    public void testGetConverterWithArgumentedConstructor() throws Throwable {
        try {
            provider.getConverter(byte.class);
        } catch (ConverterInitializationException e) {
            assertNotNull(e.getCause());
            throw e.getCause();
        }
        fail("A ConverterInitializationException was expected.");
    }

    @Test(expected = IllegalAccessException.class)
    public void testGetConverterWithPrivateConstructor() throws Throwable {
        try {
            provider.getConverter(short.class);
        } catch (ConverterInitializationException e) {
            assertNotNull(e.getCause());
            throw e.getCause();
        }
        fail("A ConverterInitializationException was expected.");
    }

    @Test
    public void testGetExtensibleConverter() throws ConverterInitializationException {
        // for something not existing in original project
        assertConverterClass(DoubleConverter.class, Double.class);
        /* TODO ensure that, when using inside another project, the local poject overrides */
        // replacing an existing converter
        // assertConverterClass(ExtendedDateConverter.class, Date.class);
    }

    @Test(expected = ConverterInitializationException.class)
    public void testGetNonConverterDescriptor() throws ConverterInitializationException {
        // descriptor file does not link to a Converter
        provider.getConverter(Character.class);
    }

    @Test(expected = ConverterInitializationException.class)
    public void testGetNonExistingConverterDescriptor() throws ConverterInitializationException {
        // descriptor file links to a non-existent class
        provider.getConverter(Byte.class);
    }

    private void assertConverterClass(Class<?> expectedConverterClass, Class<?> fieldClass)
            throws ConverterInitializationException {
        assertEquals(expectedConverterClass, provider.getConverter(fieldClass).getClass());
    }
}
