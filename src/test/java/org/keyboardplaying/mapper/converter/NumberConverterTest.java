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
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.Test;
import org.keyboardplaying.mapper.exception.ConversionException;

/**
 * @author cyChop (http://keyboardplaying.org/)
 */
public class NumberConverterTest {

    private IntegerConverter ic = new IntegerConverter();
    private LongConverter lc = new LongConverter();
    private BigIntegerConverter bic = new BigIntegerConverter();

    @Test
    public void testConvertStringToInteger() throws ConversionException {
        assertEquals(Integer.valueOf(1), ic.convertFromString("1", -1));
        assertEquals(Integer.valueOf(1), ic.convertFromString("1", null));
        assertEquals(Integer.valueOf(-1), ic.convertFromString(null, -1));
        assertNull(ic.convertFromString(null, null));
        try {
            ic.convertFromString("notANumber", -1);
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertIntegerToString() {
        assertEquals("1", ic.convertToString(1, -1));
        assertEquals("1", ic.convertToString(Integer.valueOf(1), null));
        assertEquals("-1", ic.convertToString(null, -1));
        assertNull(ic.convertToString(null, null));
    }

    @Test
    public void testConvertStringToLong() throws ConversionException {
        assertEquals(Long.valueOf(1), lc.convertFromString("1", -1L));
        assertEquals(Long.valueOf(1), lc.convertFromString("1", null));
        assertEquals(Long.valueOf(-1), lc.convertFromString(null, -1L));
        assertNull(lc.convertFromString(null, null));
        try {
            lc.convertFromString("notANumber", -1L);
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertLongToString() {
        assertEquals("1", lc.convertToString(1L, -1L));
        assertEquals("1", lc.convertToString(Long.valueOf(1), null));
        assertEquals("-1", lc.convertToString(null, -1L));
        assertNull(lc.convertToString(null, null));
    }

    @Test
    public void testConvertStringToBigInteger() throws ConversionException {
        assertEquals(BigInteger.valueOf(1), bic.convertFromString("1", BigInteger.valueOf(-1)));
        assertEquals(BigInteger.valueOf(1), bic.convertFromString("1", null));
        assertEquals(BigInteger.valueOf(-1), bic.convertFromString(null, BigInteger.valueOf(-1)));
        assertNull(bic.convertFromString(null, null));
        try {
            bic.convertFromString("notANumber", BigInteger.valueOf(-1));
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertBigIntegerToString() {
        assertEquals("1", bic.convertToString(BigInteger.valueOf(1), BigInteger.valueOf(-1)));
        assertEquals("1", bic.convertToString(BigInteger.valueOf(1), null));
        assertEquals("-1", bic.convertToString(null, BigInteger.valueOf(-1)));
        assertNull(bic.convertToString(null, null));
    }
}
