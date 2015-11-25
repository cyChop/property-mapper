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
import org.keyboardplaying.mapper.annotation.Nested;
import org.keyboardplaying.mapper.annotation.TemporalType;
import org.keyboardplaying.mapper.exception.MapperException;
import org.keyboardplaying.mapper.exception.MappingException;
import org.keyboardplaying.mapper.mock.bean.TestBean;
import org.keyboardplaying.mapper.mock.bean.TestInnerImpl;

// TODO test custom getter
/**
 * Tests for the {@link MappingEngine}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
@SuppressWarnings("javadoc")
public class MappingEngineTest {

    private MappingEngine mappingEngine = new MappingEngine();

    /** Tests mapping a {@code null} bean. */
    @Test(expected = NullPointerException.class)
    public void testMapWithNullBean() throws MapperException {
        mappingEngine.map(null);
    }

    /** Tests the mapping of a {@code null} bean while specifying the map. */
    @Test(expected = NullPointerException.class)
    public void testMapWithNullBean2() throws MapperException {
        mappingEngine.map(null, new HashMap<String, String>());
    }

    /** Tests the mapping of a bean to a {@code null} map. */
    @Test(expected = NullPointerException.class)
    public void testMapWithNullMap() throws MapperException {
        mappingEngine.map(new TestBean(), null);
    }

    /** Tests the mapping of a bean with a {@link Nested} field. */
    @Test
    public void testMapNested() throws MapperException {
        /* Prepare */
        // bean
        TestInnerImpl innerBean = new TestInnerImpl();
        innerBean.setHello("hello");
        TestBean bean = new TestBean();
        bean.setInnerImpl(innerBean);
        // expected map
        Map<String, String> expected = makeEmptyExpectedMap();
        expected.put("hello_world_inner", "hello");

        /* Execute */
        Map<String, String> map = mappingEngine.map(bean);

        /* Assert */
        assertContentEquals(expected, map);
    }

    // TODO test @DefaultValue, field not set
    // TODO test @DefaultValue, field set

    /** Tests the mapping of a bean with a mandatory field left blank. */
    @Test(expected = MappingException.class)
    public void testMapWithMandatoryFieldNotSet() throws MapperException {
        /* Prepare */
        TestBean bean = new TestBean();
        bean.setInnerImpl(new TestInnerImpl());

        /* Execute */
        mappingEngine.map(bean);
    }

    // TODO test @DefaultValue overwriting map
    // TODO test metadata overwriting map
    // TODO test custom getter

    /** Tests the mapping of a bean to a map. */
    @Test
    public void testMapToBean() throws MapperException {
        /* Prepare */
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        // bean
        TestBean bean = new TestBean();
        bean.setSomeBool(true);
        bean.setSomeInt(42);
        bean.setSomeLong(4815162342L);
        bean.setSomeBig(BigInteger.valueOf(1337));
        bean.setCal(cal);
        // expected map
        Map<String, String> expected = makeEmptyExpectedMap();
        expected.put("some_bool", "true");
        expected.put("some_int", "42");
        expected.put("some_long", "4815162342");
        expected.put("some_bigint", "1337");
        expected.put("some_important_date", new SimpleDateFormat(TemporalType.DATETIME.getFormat()).format(now));

        /* Execute */
        Map<String, String> map = mappingEngine.map(bean);

        /* Assert */
        assertContentEquals(expected, map);
    }

    private Map<String, String> makeEmptyExpectedMap() {
        Map<String, String> expected = new HashMap<>();
        expected.put("hello_world", "Did not say hello... :(");
        expected.put("some_bool", "false");
        expected.put("some_int", "0");
        expected.put("some_long", null);
        expected.put("some_bigint", null);
        expected.put("somebody_s_name", null);
        expected.put("somebody_s_phone", null);
        expected.put("some_even_more_important_date", null);
        expected.put("some_important_date", null);
        return expected;
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
