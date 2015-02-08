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
import org.keyboardplaying.mapper.utils.Defaults;

// XXX Javadoc
/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class BooleanConverterTest {

    private BooleanConverter enC = new BooleanConverter();
    private BooleanConverter frC = new BooleanConverter();

    {
        frC.setTrueFalse(Defaults.BOOLEAN_TRUE_FR, Defaults.BOOLEAN_FALSE_FR);
    }

    @Test
    public void testConvertFromString() throws ConversionException {
        assertTrue(enC.convertFromString(Defaults.BOOLEAN_TRUE));
        assertFalse(enC.convertFromString(Defaults.BOOLEAN_FALSE));
        try {
            enC.convertFromString(Defaults.BOOLEAN_FALSE_FR);
            fail();
        } catch (ConversionException e) {
            // incorrect value
        } catch (Exception e) {
            fail();
        }

        assertTrue(frC.convertFromString(Defaults.BOOLEAN_TRUE_FR));
        assertFalse(frC.convertFromString(Defaults.BOOLEAN_FALSE_FR));
        try {
            frC.convertFromString(Defaults.BOOLEAN_TRUE);
            fail();
        } catch (ConversionException e) {
            // incorrect value
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertToString() throws ConversionException {
        assertEquals(Defaults.BOOLEAN_TRUE, enC.convertToString(true));
        assertEquals(Defaults.BOOLEAN_FALSE, enC.convertToString(false));
        assertEquals(Defaults.BOOLEAN_TRUE_FR, frC.convertToString(true));
        assertEquals(Defaults.BOOLEAN_FALSE_FR, frC.convertToString(false));
    }
}
