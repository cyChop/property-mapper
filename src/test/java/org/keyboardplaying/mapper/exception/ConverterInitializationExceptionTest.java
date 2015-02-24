package org.keyboardplaying.mapper.exception;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

/**
 * Tests each constructor for {@link ConverterInitializationException}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ConverterInitializationExceptionTest extends
        MapperExceptionTest<ConverterInitializationException> {

    /** Creates a new instance. */
    public ConverterInitializationExceptionTest() {
        super(ConverterInitializationException.class);
    }

    /** Tests {@link ConverterInitializationException#ConverterInitializationException()}. */
    @Test
    @Override
    public void testNoArgConstructor() throws InstantiationException, IllegalAccessException {
        super.testNoArgConstructor();
    }

    /** Tests {@link ConverterInitializationException#ConverterInitializationException(String)}. */
    @Test
    @Override
    public void testMessageConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        super.testMessageConstructor();
    }

    /** Tests {@link ConverterInitializationException#ConverterInitializationException(Throwable)}. */
    @Test
    @Override
    public void testCauseConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        super.testCauseConstructor();
    }

    /**
     * Tests
     * {@link ConverterInitializationException#ConverterInitializationException(String, Throwable)}.
     */
    @Test
    @Override
    public void testBothConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        super.testBothConstructor();
    }
}
