package org.keyboardplaying.mapper.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

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
import org.keyboardplaying.mapper.parser.CalendarParser;
import org.keyboardplaying.mapper.parser.DateParser;

/**
 * Tests for the {@link UnmappingEngine}.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
@SuppressWarnings("javadoc")
public class UnmappingEngineTest {

    private final UnmappingEngine mappingEngine = new UnmappingEngine();

    /**
     * Tests the {@link Nested} annotation.
     */
    @Test
    public void testUnmapNested() throws MapperException {
        /* Prepare */
        Map<String, String> metadata = makeMinimalMetadata();
        metadata.put("hello_world_inner", "Hello, Little Big Planet!");

        /* Execute */
        TestBean bean = mappingEngine.unmapToClass(metadata, TestBean.class);

        /* Assert */
        // autowired nested (on a class)
        assertEquals("Hello, Little Big Planet!", bean.getInnerImpl().getHello());
        // declared nested (on an interface)
        assertEquals(TestInnerImpl.class, bean.getInnerItf().getClass());
        assertEquals("Hello, Little Big Planet!", bean.getInnerItf().getHello());
    }

    /**
     * Ensures the unmapping fails when the {@link Nested}-annotated field is not instantiable and has no implementation
     * specification.
     */
    @Test(expected = MappingException.class)
    public void testUnmapUnspecifiedNested() throws MapperException {
        /* Prepare */
        Map<String, String> metadata = makeMinimalMetadata();

        /* Execute */
        mappingEngine.unmapToBean(metadata, new Object() {
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
        });
    }

    /**
     * Ensures the unmapping fails when the {@link Nested}-annotated field is specified to an implementation which does
     * not exist.
     */
    @Test(expected = MappingException.class)
    public void testUnmapUnexistingNested() throws MapperException {
        /* Prepare */
        Map<String, String> metadata = makeMinimalMetadata();

        /* Execute */
        mappingEngine.unmapToBean(metadata, new Object() {
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
        });
    }

    /**
     * Tests the default value when no value is set.
     */
    @Test
    public void testUnmapDefaultValueNotSet() throws MapperException {
        /* Prepare */
        Map<String, String> metadata1 = makeMinimalMetadata();
        Map<String, String> metadata2 = new HashMap<>();
        metadata2.put("the_doctor", "David Tennant");

        /* Execute */
        TestBean bean1 = mappingEngine.unmapToClass(metadata1, TestBean.class);
        TestDefaultedBean bean2 = mappingEngine.unmapToClass(metadata2, TestDefaultedBean.class);

        /* Assert */
        assertEquals("Didn't receive hello... :(", bean1.getHello());
        assertEquals(Integer.valueOf(42), bean2.getTheAnswer());
        assertEquals("", bean2.getNotNullString());
    }

    /**
     * Tests the unmapping of a bean with mandatory metadata left blank.
     */
    @Test(expected = MappingException.class)
    public void testUnmapWithMandatoryMetadataNotSet() throws MapperException {
        /* Prepare */
        Map<String, String> metadata = new HashMap<>();

        /* Execute */
        mappingEngine.unmapToClass(metadata, TestBean.class);
    }

    /**
     * Tests the unmapping of a bean with a mandatory metadata with a default metadata but no default value.
     */
    @Test(expected = MappingException.class)
    public void testUnmapMandatoryFieldWithDefaultValueNotSet() throws MapperException {
        /* Prepare */
        Map<String, String> metadata = new HashMap<>();

        /* Execute */
        mappingEngine.unmapToClass(metadata, TestDefaultedBean.class);
    }

    /**
     * Tests the default value when a value is set.
     */
    @Test
    public void testUnmapDefaultValueSet() throws MapperException {
        /* Prepare */
        Map<String, String> metadata = makeMinimalMetadata();
        metadata.put("hello_world", "Hello, World!");

        /* Execute */
        TestBean bean = mappingEngine.unmapToClass(metadata, TestBean.class);

        /* Assert */
        assertEquals("Hello, World!", bean.getHello());
    }

    /**
     * Tests the unmapping (using the default value) to a previously set bean.
     */
    @Test
    public void testUnmapToExistingBeanDefault() throws MapperException {
        /* Prepare */
        Map<String, String> metadata = makeMinimalMetadata();
        TestBean bean = new TestBean();
        bean.setHello("Honey, I'm home! Oh, forgot... I'm not married.");

        /* Execute */
        bean = mappingEngine.unmapToClass(metadata, TestBean.class);

        /* Assert */
        assertEquals("Didn't receive hello... :(", bean.getHello());
    }

    /**
     * Tests the unmapping to a previously set bean.
     */
    @Test
    public void testUnmapToExistingBeanMetadata() throws MapperException {
        /* Prepare */
        Map<String, String> metadata = makeMinimalMetadata();
        metadata.put("hello_world", "Hello, World!");
        metadata.put("hello_world_inner", "Hello, Little Big Planet!");
        TestBean bean = new TestBean();
        TestInnerImpl inner = new TestInnerImpl();
        inner.setHello("Yup, that's me!");
        bean.setInnerImpl(inner);
        bean.setHello("Honey, I'm home! Oh, forgot... I'm not married.");

        /* Execute */
        bean = mappingEngine.unmapToBean(metadata, bean);

        /* Assert */
        assertEquals("Hello, World!", bean.getHello());
        assertEquals("Hello, Little Big Planet!", bean.getInnerImpl().getHello());
    }

    /**
     * Tests the unmapping is null-proof.
     */
    @Test
    public void testUnmapWithNullData() throws MapperException {
        /* Prepare */
        Map<String, String> metadata = makeMinimalMetadata();
        // test against primitive and object
        metadata.put("hello_world", null);
        metadata.put("some_number", null);

        /* Execute */
        TestBean bean = mappingEngine.unmapToClass(metadata, TestBean.class);

        /* Assert */
        assertEquals(null, bean.getHello());
        assertEquals(0, bean.getSomeInt());
    }

    /**
     * Tests the unmapping using an ElaborateParser.
     */
    @Test
    public void testUnmapUsingElaborateParser() throws MapperException {
        /* Prepare */
        Map<String, String> metadata = makeMinimalMetadata();
        metadata.put("somebody_s_name", "John DOE");
        metadata.put("somebody_s_phone", "4815162342");

        /* Execute */
        TestBean bean = mappingEngine.unmapToClass(metadata, TestBean.class);

        /* Assert */
        assertEquals("John DOE (4815162342)", bean.getContact());
    }

    /**
     * Tests the unmapping of a boolean in an incorrect format.
     */
    @Test(expected = MappingException.class)
    public void testUnmapIncorrectBoolean() throws MapperException {
        /* Prepare */
        Map<String, String> metadata = makeMinimalMetadata();
        metadata.put("some_bool", "YES");

        /* Execute */
        mappingEngine.unmapToClass(metadata, TestBean.class);
    }

    /**
     * Tests the unmapping of a data map with several types of metadata.
     */
    @Test
    public void testUnmapToBean() throws MapperException {
        /* Prepare */
        Map<String, String> metadata = makeMinimalMetadata();
        metadata.put("some_int", "42");
        metadata.put("some_long", "4815162342");
        metadata.put("some_bigint", "1337");
        metadata.put("some_important_date", "1985/10/24-21:20:00");
        metadata.put("some_even_more_important_date", "2012/06/29");
        metadata.put("some_bool", "true");
        metadata.put("some_yesno", "yes");

        /* Execute */
        TestBean bean = mappingEngine.unmapToClass(metadata, TestBean.class);

        /* Assert */
        assertEquals(42, bean.getSomeInt());
        assertEquals(Long.valueOf(4815162342L), bean.getSomeLong());
        assertEquals(BigInteger.valueOf(1337), bean.getSomeBig());
        // calendar comparison
        CalendarParser calConv = new CalendarParser();
        calConv.setFormat(TemporalType.DATETIME.getFormat());
        assertEquals(calConv.convertFromString(metadata.get("some_important_date")), bean.getCal());
        // date comparison
        DateParser dateConv = new DateParser();
        dateConv.setFormat(TemporalType.DATE.getFormat());
        assertEquals(dateConv.convertFromString(metadata.get("some_even_more_important_date")), bean.getDate());
        // boolean testing
        assertTrue(bean.isSomeBool());
        assertTrue(bean.isSomeYesNo());
    }

    /**
     * Tests the unmapping in case of an inherited field.
     */
    @Test
    public void testUnmapSubclassedBean() throws MapperException {
        /* Prepare */
        Map<String, String> metadata = makeMinimalMetadata();
        metadata.put("hello_world_sub", "I'll take a sub-teryaki, please.");

        /* Execute */
        TestSubBean bean = mappingEngine.unmapToClass(metadata, TestSubBean.class);

        /* Assert */
        // declared fields
        assertEquals("I'll take a sub-teryaki, please.", bean.getHelloSub());
        // inherited fields
        assertEquals("Didn't receive hello... :(", bean.getHello());
    }

    private Map<String, String> makeMinimalMetadata() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Do do do", "De da da da");
        return metadata;
    }

    /**
     * Ensures the mapping fails if no key was supplied.
     */
    @Test(expected = MappingException.class)
    public void testUnmapWithEmptyMetadataKey() throws MapperException {
        /* Prepare */
        Map<String, String> metadata = makeMinimalMetadata();

        /* Execute */
        mappingEngine.unmapToBean(metadata, new Object() {
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
        });
    }

    /**
     * Ensures the mapping fails if the setter is absent.
     */
    @Test(expected = MappingException.class)
    public void testUnmapWithNoSetter() throws MapperException {
        /* Prepare */
        Map<String, String> metadata = makeMinimalMetadata();
        metadata.put("hello", "world");

        /* Execute */
        mappingEngine.unmapToBean(metadata, new Object() {
            @Metadata("hello")
            private String hello;

            @SuppressWarnings("unused")
            public String getHello() {
                return hello;
            }
        });
    }
}
