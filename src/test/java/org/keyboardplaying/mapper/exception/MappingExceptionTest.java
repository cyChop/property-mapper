package org.keyboardplaying.mapper.exception;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

/**
 * Tests each constructor for {@link MappingException}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class MappingExceptionTest extends MapperExceptionTest<MappingException> {

    /** Creates a new instance. */
    public MappingExceptionTest() {
        super(MappingException.class);
    }

    /** Tests {@link MappingException#MappingException()}. */
    @Test
    @Override
    public void testNoArgConstructor() throws InstantiationException, IllegalAccessException {
        super.testNoArgConstructor();
    }

    /** Tests {@link MappingException#MappingException(String)}. */
    @Test
    @Override
    public void testMessageConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        super.testMessageConstructor();
    }

    /** Tests {@link MappingException#MappingException(Throwable)}. */
    @Test
    @Override
    public void testCauseConstructor() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        super.testCauseConstructor();
    }

    /** Tests {@link MappingException#MappingException(String, Throwable)}. */
    @Test
    @Override
    public void testBothConstructor() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        super.testBothConstructor();
    }
}
