package org.keyboardplaying.mapper.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.keyboardplaying.mapper.annotation.TemporalType;
import org.keyboardplaying.mapper.exception.MapperException;
import org.keyboardplaying.mapper.exception.MappingException;
import org.keyboardplaying.mapper.mock.bean.TestBean;
import org.keyboardplaying.mapper.mock.bean.TestInnerImpl;
import org.keyboardplaying.mapper.parser.CalendarParser;
import org.keyboardplaying.mapper.parser.DateParser;

/**
 * Tests for the {@link UnmappingEngine}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
@SuppressWarnings("javadoc")
public class UnmappingEngineTest {

    private UnmappingEngine mappingEngine = new UnmappingEngine();

    /** Tests the unmapping of an empty data map. */
    @Test(expected = MappingException.class)
    public void testMapWithMissingMandatory() throws MapperException {
        mappingEngine.unmap(new HashMap<String, String>(), TestBean.class);
    }

    /** Tests the unmapping of a data map. */
    @Test
    public void testMapToBean() throws MapperException {
        Map<String, String> metadata = new HashMap<>();

        /* Test @Nested & @DefaultValue */
        metadata.put("hello_world_inner", "Hello, Little Big Planet!");
        TestBean bean = mappingEngine.unmap(metadata, TestBean.class);
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
        bean = mappingEngine.unmap(metadata, bean);
        assertEquals("Did not say hello... :(", bean.getHello());
        // overwrite with metadata
        metadata.put("hello_world", "Hello, World.");
        bean = mappingEngine.unmap(metadata, bean);
        assertEquals("Hello, World.", bean.getHello());

        /* Test various data types. */
        metadata.put("some_number", "42");
        metadata.put("some_important_date", "1985/10/24-21:20:00");
        metadata.put("some_even_more_important_date", "2012/06/29");
        bean = mappingEngine.unmap(metadata, bean);
        assertEquals(42, bean.getSomeInt());
        assertEquals(Long.valueOf(42), bean.getSomeLong());
        assertEquals(BigInteger.valueOf(42), bean.getSomeBig());
        // calendar comparison
        CalendarParser calConv = new CalendarParser();
        calConv.setFormat(TemporalType.DATETIME.getFormat());
        assertEquals(calConv.convertFromString(metadata.get("some_important_date")), bean.getCal());
        // date comparison
        DateParser dateConv = new DateParser();
        dateConv.setFormat(TemporalType.DATE.getFormat());
        assertEquals(dateConv.convertFromString(metadata.get("some_even_more_important_date")), bean.getDate());
        // boolean testing
        metadata.put("some_bool", "YES");
        try {
            bean = mappingEngine.unmap(metadata, TestBean.class);
            // fail();
        } catch (MappingException e) {
            // the boolean is not in the expected format
        } catch (Exception e) {
            fail();
        }
        assertFalse(bean.isSomeBool());
        metadata.put("some_bool", "true");
        bean = mappingEngine.unmap(metadata, TestBean.class);
        assertTrue(bean.isSomeBool());

        /* Test custom setter. */
        metadata.put("somebody_s_name", "John DOE");
        metadata.put("somebody_s_phone", "4815162342");
        bean = mappingEngine.unmap(metadata, TestBean.class);
        assertEquals("John DOE (4815162342)", bean.getContact());

        /* Ensure null-proof. */
        // test Object and primitive types
        metadata.put("hello_world", null);
        metadata.put("some_number", null);
        bean = mappingEngine.unmap(metadata, bean);
        assertEquals(null, bean.getHello());
        assertEquals(0, bean.getSomeInt());
    }
}
