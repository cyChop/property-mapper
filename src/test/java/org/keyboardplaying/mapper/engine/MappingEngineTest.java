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

import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ObjectUtils;
import org.junit.Test;
import org.keyboardplaying.mapper.annotation.TemporalType;
import org.keyboardplaying.mapper.bean.TestBean;
import org.keyboardplaying.mapper.bean.TestInnerImpl;
import org.keyboardplaying.mapper.exception.ConversionException;
import org.keyboardplaying.mapper.exception.MappingException;

// TODO test custom getter
/**
 * @author cyChop (http://keyboardplaying.org/)
 */
public class MappingEngineTest {

    private MappingEngine mappingEngine = new MappingEngine();

    @Test
    public void testMapToBean() throws MappingException, ConversionException {
        TestBean bean = null;

        try {
            mappingEngine.map(bean);
            fail();
        } catch (MappingException e) {
            // bean is null
        } catch (Exception e) {
            fail();
        }

        bean = new TestBean();
        bean.setSomeInt(42);
        bean.setSomeLong(42L);
        bean.setSomeBig(BigInteger.valueOf(42));

        Map<String, String> expected = new HashMap<String, String>();
        expected.put("hello_world", "Did not say hello... :(");
        expected.put("some_bool", "false");
        expected.put("some_number", "42");
        expected.put("somebody_s_name", null);
        expected.put("some_important_date", null);
        expected.put("some_even_more_important_date", null);
        assertContentEquals(expected, mappingEngine.map(bean));

        bean.setInnerImpl(new TestInnerImpl());
        try {
            mappingEngine.map(bean);
            fail();
        } catch (MappingException e) {
            // mandatory without value nor default
        } catch (Exception e) {
            fail();
        }

        bean.getInnerImpl().setHello("hello");
        expected.put("hello_world_inner", "hello");
        assertContentEquals(expected, mappingEngine.map(bean));

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        bean.setCal(cal);
        expected.put("some_important_date", new SimpleDateFormat(TemporalType.DATETIME.getFormat()).format(now));
        assertContentEquals(expected, mappingEngine.map(bean));
    }

    private static void assertContentEquals(Map<?, ?> expected, Map<?, ?> actual) {
        if (expected.size() != actual.size()) {
            fail("Expected " + expected + " but found " + actual);
        }

        for (Entry<?, ?> entry : expected.entrySet()) {
            if (!actual.containsKey(entry.getKey())
                    || !ObjectUtils.equals(entry.getValue(), actual.get(entry.getKey()))) {
                fail("Expected " + entry.getKey() + "=" + entry.getValue() + " but found " + entry.getKey() + "="
                        + actual.get(entry.getKey()));
            }
        }
    }
}
