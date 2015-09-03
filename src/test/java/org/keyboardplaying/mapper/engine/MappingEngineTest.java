package org.keyboardplaying.mapper.engine;

import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.junit.Test;
import org.keyboardplaying.mapper.annotation.TemporalType;
import org.keyboardplaying.mapper.exception.MapperException;
import org.keyboardplaying.mapper.exception.MappingException;
import org.keyboardplaying.mapper.exception.ParserInitializationException;
import org.keyboardplaying.mapper.mock.bean.TestBean;
import org.keyboardplaying.mapper.mock.bean.TestInnerImpl;

// TODO test custom getter
// XXX Javadoc
/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class MappingEngineTest {

    private MappingEngine mappingEngine = new MappingEngine();

    @Test(expected = NullPointerException.class)
    public void testMapWithNullBean() throws ParserInitializationException, MappingException {
        mappingEngine.map(null);
    }

    @Test(expected = NullPointerException.class)
    public void testMapWithNullBean2() throws ParserInitializationException, MappingException {
        mappingEngine.map(null, new HashMap<String, String>());
    }

    @Test(expected = NullPointerException.class)
    public void testMapWithNullMap() throws ParserInitializationException, MappingException {
        mappingEngine.map(new TestBean(), null);
    }

    @Test
    public void testMapToBean() throws MapperException {

        TestBean bean = new TestBean();
        bean.setSomeInt(42);
        bean.setSomeLong(42L);
        bean.setSomeBig(BigInteger.valueOf(42));

        Map<String, String> expected = new HashMap<>();
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
            if (!actual.containsKey(entry.getKey()) || !Objects.equals(entry.getValue(), actual.get(entry.getKey()))) {
                fail("Expected " + entry.getKey() + "=" + entry.getValue() + " but found " + entry.getKey() + "="
                        + actual.get(entry.getKey()));
            }
        }
    }
}
