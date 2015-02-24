package org.keyboardplaying.mapper.exception;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

/**
 * Tests each constructor for {@link ConversionException}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ConversionExceptionTest extends MapperExceptionTest<ConversionException> {

    /** Creates a new instance. */
    public ConversionExceptionTest() {
        super(ConversionException.class);
    }

    /** Tests {@link ConversionException#ConversionException()}. */
    @Test
    @Override
    public void testNoArgConstructor() throws InstantiationException, IllegalAccessException {
        super.testNoArgConstructor();
    }

    /** Tests {@link ConversionException#ConversionException(String)}. */
    @Test
    @Override
    public void testMessageConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        super.testMessageConstructor();
    }

    /** Tests {@link ConversionException#ConversionException(Throwable)}. */
    @Test
    @Override
    public void testCauseConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        super.testCauseConstructor();
    }

    /** Tests {@link ConversionException#ConversionException(String, Throwable)}. */
    @Test
    @Override
    public void testBothConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        super.testBothConstructor();
    }
}
