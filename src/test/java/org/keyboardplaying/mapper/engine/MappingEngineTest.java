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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.keyboardplaying.mapper.annotation.TemporalType;
import org.keyboardplaying.mapper.bean.TestBean;
import org.keyboardplaying.mapper.bean.TestInnerImpl;
import org.keyboardplaying.mapper.converter.CalendarConverter;
import org.keyboardplaying.mapper.exception.MappingException;

/**
 * @author cyChop (http://keyboardplaying.org/)
 */
public class MappingEngineTest {

    private MappingEngine mappingEngine = new MappingEngine();

    @Test
    public void testMapToBean() throws MappingException {
        Map<String, String> metadata = new HashMap<String, String>();

        /* Test @Nested & @Metadata(mandatory = true) */
        // The metadata of nested beans is mandatory.
        try {
            mappingEngine.mapToBean(metadata, TestBean.class);
            fail();
        } catch (MappingException e) {
            // a mandatory field is missing from metadata ('hello_world_inner')
        } catch (Exception e) {
            fail();
        }

        /* Test @Nested & @DefaultValue */
        metadata.put("hello_world_inner", "Hello, Little Big Planet!");
        TestBean bean = mappingEngine.mapToBean(metadata, TestBean.class);
        // default value
        assertEquals("Did not say hello... :(", bean.getHello());
        // autowired nested
        assertEquals("Hello, Little Big Planet!", bean.getInnerImpl().getHello());
        // declared nested
        assertEquals(TestInnerImpl.class, bean.getInnerItf().getClass());
        assertEquals("Hello, Little Big Planet!", bean.getInnerItf().getHello());

        /* Test overwriting an existing bean. */
        // overwrite with default value
        bean.setHello("Honey, I'm home! Oh, forgot... I'm not married.");
        bean = mappingEngine.mapToBean(metadata, bean);
        assertEquals("Did not say hello... :(", bean.getHello());
        // overwrite with metadata
        metadata.put("hello_world", "Hello, World.");
        bean = mappingEngine.mapToBean(metadata, bean);
        assertEquals("Hello, World.", bean.getHello());

        /* Test various data types. */
        metadata.put("some_number", "42");
        metadata.put("some_important_date", "1985/10/24-21:20:00");
        metadata.put("some_even_more_important_date", "2012/06/29");
        bean = mappingEngine.mapToBean(metadata, bean);
        assertEquals(42, bean.getSomeInt());
        assertEquals(42L, bean.getSomeLong());
        assertEquals(BigInteger.valueOf(42), bean.getSomeBig());
        // calendar comparison
        CalendarConverter calConv = new CalendarConverter();
        calConv.setFormat(TemporalType.DATETIME.getFormat());

        /* Ensure null-proof */
        // test Object and primitive types
        metadata.put("hello_world", null);
        metadata.put("some_number", null);
        bean = mappingEngine.mapToBean(metadata, bean);
        assertEquals(null, bean.getHello());
        assertEquals(0, bean.getSomeInt());
    }
}
