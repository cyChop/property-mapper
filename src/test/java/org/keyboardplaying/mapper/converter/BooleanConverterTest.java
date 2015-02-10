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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.keyboardplaying.mapper.exception.ConversionException;
import org.keyboardplaying.mapper.Defaults;

// XXX Javadoc
/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class BooleanConverterTest {

    private BooleanConverter enC = new BooleanConverter();
    private BooleanConverter frC = new BooleanConverter();

    {
        frC.setTrueFalse(Defaults.BOOLEAN_YES_FR, Defaults.BOOLEAN_NO_FR);
    }

    @Test
    public void testConvertFromString() throws ConversionException {
        assertTrue(enC.convertFromString(Defaults.BOOLEAN_YES));
        assertFalse(enC.convertFromString(Defaults.BOOLEAN_NO));
        try {
            enC.convertFromString(Defaults.BOOLEAN_NO_FR);
            fail();
        } catch (ConversionException e) {
            // incorrect value
        } catch (Exception e) {
            fail();
        }

        assertTrue(frC.convertFromString(Defaults.BOOLEAN_YES_FR));
        assertFalse(frC.convertFromString(Defaults.BOOLEAN_NO_FR));
        try {
            frC.convertFromString(Defaults.BOOLEAN_YES);
            fail();
        } catch (ConversionException e) {
            // incorrect value
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertToString() throws ConversionException {
        assertEquals(Defaults.BOOLEAN_YES, enC.convertToString(true));
        assertEquals(Defaults.BOOLEAN_NO, enC.convertToString(false));
        assertEquals(Defaults.BOOLEAN_YES_FR, frC.convertToString(true));
        assertEquals(Defaults.BOOLEAN_NO_FR, frC.convertToString(false));
    }

    @Test
    public void testNull() throws ConversionException {
        assertEquals(Defaults.BOOLEAN_NO, enC.convertToString(null));
        assertEquals(Defaults.BOOLEAN_NO_FR, frC.convertToString(null));
    }
}
