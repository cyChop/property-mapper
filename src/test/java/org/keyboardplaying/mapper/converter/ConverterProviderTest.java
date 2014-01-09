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
import static org.junit.Assert.assertNull;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

/**
 * @author cyChop (http://keyboardplaying.org/)
 */
public class ConverterProviderTest {

    @Test
    public void testGetConverter() {
        ConverterProvider provider = new ConverterProvider();

        assertEquals(StringConverter.class, provider.getConverter(String.class).getClass());
        assertEquals(BooleanConverter.class, provider.getConverter(Boolean.class).getClass());
        assertEquals(BooleanConverter.class, provider.getConverter(boolean.class).getClass());
        assertEquals(IntegerConverter.class, provider.getConverter(Integer.class).getClass());
        assertEquals(IntegerConverter.class, provider.getConverter(int.class).getClass());
        assertEquals(LongConverter.class, provider.getConverter(Long.class).getClass());
        assertEquals(LongConverter.class, provider.getConverter(long.class).getClass());
        assertEquals(BigIntegerConverter.class, provider.getConverter(BigInteger.class).getClass());
        assertEquals(DateConverter.class, provider.getConverter(Date.class).getClass());
        assertEquals(CalendarConverter.class, provider.getConverter(Calendar.class).getClass());
        assertNull(provider.getConverter(char.class));
    }

    /*
     * This should work but does not. It appears ResourceFinder ignores the
     * test/resources/META-INF. Class loader/classpath problem?
     */
    // @Test
    // public void testExtensibleGetConverter() {
    // ConverterProvider provider = new ConverterProvider();
    //
    // assertEquals(DoubleConverter.class,
    // provider.getConverter(Double.class).getClass());
    // assertEquals(DoubleConverter.class,
    // provider.getConverter(double.class).getClass());
    // }
}
