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
import org.keyboardplaying.mapper.annotation.Metadata;
import org.keyboardplaying.mapper.annotation.Nested;
import org.keyboardplaying.mapper.annotation.Temporal.TemporalType;
import org.keyboardplaying.mapper.exception.MapperException;
import org.keyboardplaying.mapper.exception.MappingException;
import org.keyboardplaying.mapper.mock.bean.TestBean;
import org.keyboardplaying.mapper.mock.bean.TestDefaultedBean;
import org.keyboardplaying.mapper.mock.bean.TestInnerBean;
import org.keyboardplaying.mapper.mock.bean.TestInnerImpl;
import org.keyboardplaying.mapper.mock.bean.TestSubBean;

/**
 * Tests for the {@link MappingEngine}.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
@SuppressWarnings("javadoc")
public class MappingEngineTest {

    private final MappingEngine mappingEngine = new MappingEngine();

    /**
     * Tests mapping a {@code null} bean.
     */
    @Test(expected = NullPointerException.class)
    public void testMapWithNullBean() throws MapperException {
        mappingEngine.map(null);
    }

    /**
     * Tests the mapping of a {@code null} bean while specifying the map.
     */
    @Test(expected = NullPointerException.class)
    public void testMapWithNullBean2() throws MapperException {
        mappingEngine.map(null, new HashMap<>());
    }

    /**
     * Tests the mapping of a bean to a {@code null} map.
     */
    @Test(expected = NullPointerException.class)
    public void testMapWithNullMap() throws MapperException {
        mappingEngine.map(makeMinimalBean(), null);
    }

    /**
     * Tests the mapping of a bean with a {@link Nested} field.
     */
    @Test
    public void testMapNested() throws MapperException {
        /* Prepare */
        // bean
        TestInnerImpl innerBean = new TestInnerImpl();
        innerBean.setHello("hello");
        TestBean bean = makeMinimalBean();
        bean.setInnerImpl(innerBean);
        // expected map
        Map<String, String> expected = makeEmptyExpectedMap();
        expected.put("hello_world_inner", "hello");

        /* Execute */
        Map<String, String> map = mappingEngine.map(bean);

        /* Assert */
        assertContentEquals(expected, map);
    }

    /**
     * Tests the default value when no value is set.
     */
    @Test
    public void testDefaultValueNotSet() throws MapperException {
        /* Prepare */
        // beans
        TestBean bean1 = makeMinimalBean();
        TestDefaultedBean bean2 = new TestDefaultedBean();
        bean2.setNotNullString("I'm not null!");
        // expected map
        Map<String, String> expected1 = makeEmptyExpectedMap();
        Map<String, String> expected2 = new HashMap<>();
        expected2.put("the_answer", null);
        expected2.put("not_null_string", "I'm not null!");
        expected2.put("the_doctor", "Doctor Who?");
        expected2.put("the_companion", "");

        /* Execute */
        Map<String, String> map1 = mappingEngine.map(bean1);
        Map<String, String> map2 = mappingEngine.map(bean2);

        /* Assert */
        assertContentEquals(expected1, map1);
        assertContentEquals(expected2, map2);
    }

    /**
     * Tests the default value when a value is set.
     */
    @Test
    public void testDefaultValueSet() throws MapperException {
        /* Prepare */
        // bean
        TestBean bean = makeMinimalBean();
        bean.setHello("Hello, gorgeous!");
        // expected map
        Map<String, String> expected = makeEmptyExpectedMap();
        expected.put("hello_world", "Hello, gorgeous!");

        /* Execute */
        Map<String, String> map = mappingEngine.map(bean);

        /* Assert */
        assertContentEquals(expected, map);
    }

    /**
     * Tests the mapping of a bean with a mandatory field left blank.
     */
    @Test(expected = MappingException.class)
    public void testMapWithMandatoryFieldNotSet() throws MapperException {
        /* Prepare */
        TestBean bean = makeMinimalBean();
        bean.setInnerImpl(new TestInnerImpl());

        /* Execute */
        mappingEngine.map(bean);
    }

    /**
     * Tests the mapping of a bean with a mandatory field with a default value but no default metadata left blank.
     */
    @Test(expected = MappingException.class)
    public void testMapWithMandatoryFieldWithDefaultValueNotSet() throws MapperException {
        /* Prepare */
        TestDefaultedBean bean = new TestDefaultedBean();

        /* Execute */
        mappingEngine.map(bean);
    }

    /**
     * Tests overwriting a map with the default value for a field.
     * <p/>
     * Also ensures that existing metadata is overwritten only for fields present in class.
     */
    @Test
    public void testOverwriteDefaultValueNotSet() throws MapperException {
        /* Prepare */
        // bean
        TestBean bean = makeMinimalBean();
        // expected map
        Map<String, String> expected = makeEmptyExpectedMap();
        expected.put("some_alien_meta", "This should not be erased.");
        // overwritten map
        Map<String, String> map = new HashMap<>();
        map.put("hello_world", "That's me!");
        map.put("some_alien_meta", "This should not be erased.");

        /* Execute */
        map = mappingEngine.map(bean, map);

        /* Assert */
        assertContentEquals(expected, map);
    }

    /**
     * Tests overwriting a map with the value for a field.
     * <p/>
     * Also ensures that existing metadata is overwritten only for fields present in class.
     */
    @Test
    public void testOverwriteDefaultValueSet() throws MapperException {
        /* Prepare */
        // bean
        TestBean bean = makeMinimalBean();
        bean.setHello("That's my cue.");
        // expected map
        Map<String, String> expected = makeEmptyExpectedMap();
        expected.put("hello_world", "That's my cue.");
        expected.put("some_alien_meta", "This should not be erased.");
        // overwritten map
        Map<String, String> map = new HashMap<>();
        map.put("hello_world", "That's me!");
        map.put("some_alien_meta", "This should not be erased.");

        /* Execute */
        map = mappingEngine.map(bean, map);

        /* Assert */
        assertContentEquals(expected, map);
    }

    // TODO test custom getter

    /**
     * Tests the mapping of a bean to a map.
     */
    @Test
    public void testMapToBean() throws MapperException {
        /* Prepare */
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        // bean
        TestBean bean = makeMinimalBean();
        bean.setSomeBool(true);
        bean.setSomeYesNo(true);
        bean.setSomeInt(42);
        bean.setSomeLong(4815162342L);
        bean.setSomeBig(BigInteger.valueOf(1337));
        bean.setCal(cal);
        bean.setContact("Cyrille (555-1337)");
        // expected map
        Map<String, String> expected = makeEmptyExpectedMap();
        expected.put("some_bool", "true");
        expected.put("some_yesno", "YES");
        expected.put("some_int", "42");
        expected.put("some_long", "4815162342");
        expected.put("some_bigint", "1337");
        expected.put("some_important_date", new SimpleDateFormat(TemporalType.DATETIME.getFormat()).format(now));
        expected.put("somebody_s_name", "Cyrille");
        expected.put("somebody_s_phone", "555-1337");

        /* Execute */
        Map<String, String> map = mappingEngine.map(bean);

        /* Assert */
        assertContentEquals(expected, map);
    }

    @Test
    public void testMapSubclassedBean() throws MapperException {
        /* Prepare */
        // bean
        TestSubBean bean = new TestSubBean();
        bean.setMandatory("De da da da");
        bean.setHelloSub("Please give me a sub-teryaki...");
        // expected map
        Map<String, String> expected = makeEmptyExpectedMap();
        expected.put("hello_world_sub", "Please give me a sub-teryaki...");

        /* Execute */
        Map<String, String> map = mappingEngine.map(bean);

        /* Assert */
        assertContentEquals(expected, map);
    }

    private TestBean makeMinimalBean() {
        TestBean bean = new TestBean();
        bean.setMandatory("De da da da");
        return bean;
    }

    private Map<String, String> makeEmptyExpectedMap() {
        Map<String, String> expected = new HashMap<>();
        expected.put("hello_world", "Didn't send hello... :(");
        expected.put("Do do do", "De da da da");
        expected.put("some_bool", "false");
        expected.put("some_yesno", "NO");
        expected.put("some_int", "0");
        expected.put("some_long", null);
        expected.put("some_bigint", null);
        expected.put("somebody_s_name", null);
        expected.put("somebody_s_phone", null);
        expected.put("some_even_more_important_date", null);
        expected.put("some_important_date", null);
        return expected;
    }

    private void assertContentEquals(Map<?, ?> expected, Map<?, ?> actual) {
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

    /**
     * Ensures the mapping fails if no key was supplied for the field.
     */
    @Test(expected = MappingException.class)
    public void testMapWithEmptyMetadataKey() throws MapperException {
        /* Execute */
        mappingEngine.map(new Object() {
            @Metadata
            private String hello;

            @SuppressWarnings("unused")
            public String getHello() {
                return hello;
            }

            @SuppressWarnings("unused")
            public void setHello(String hello) {
                this.hello = hello;
            }
        }, new HashMap<>());
    }

    /**
     * Ensures the mapping fails if a mandatory nested is null.
     */
    @Test(expected = MappingException.class)
    public void testMapUnexistingNested() throws MapperException {
        /* Execute */
        mappingEngine.map(new Object() {
            @Nested(mandatory = true)
            private TestInnerBean inner;

            @SuppressWarnings("unused")
            public TestInnerBean getInner() {
                return inner;
            }

            @SuppressWarnings("unused")
            public void setInner(TestInnerBean inner) {
                this.inner = inner;
            }
        }, new HashMap<>());
    }

    /**
     * Ensures the mapping fails if the getter is absent.
     */
    @Test(expected = MappingException.class)
    public void testMapWithNoGetter() throws MapperException {
        /* Execute */
        mappingEngine.map(new Object() {
            @Metadata("hello")
            private String hello;

            @SuppressWarnings("unused")
            public void setHello(String hello) {
                this.hello = hello;
            }
        }, new HashMap<>());
    }
}
