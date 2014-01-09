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
        assertEquals(Integer.valueOf(1), ic.convertFromString("1"));
        try {
            ic.convertFromString("notANumber");
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertIntegerToString() throws ConversionException {
        assertEquals("1", ic.convertToString(1));
        assertEquals("1", ic.convertToString(Integer.valueOf(1)));
    }

    @Test
    public void testConvertStringToLong() throws ConversionException {
        assertEquals(Long.valueOf(1), lc.convertFromString("1"));
        try {
            lc.convertFromString("notANumber");
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertLongToString() throws ConversionException {
        assertEquals("1", lc.convertToString(1L));
        assertEquals("1", lc.convertToString(Long.valueOf(1)));
    }

    @Test
    public void testConvertStringToBigInteger() throws ConversionException {
        assertEquals(BigInteger.valueOf(1), bic.convertFromString("1"));
        try {
            bic.convertFromString("notANumber");
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertBigIntegerToString() throws ConversionException {
        assertEquals("1", bic.convertToString(BigInteger.valueOf(1)));
    }
}
