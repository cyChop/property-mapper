package org.keyboardplaying.mapper.exception;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

/**
 * Tests each constructor for {@link ParsingException}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ParsingExceptionTest extends MapperExceptionTest<ParsingException> {

    /** Creates a new instance. */
    public ParsingExceptionTest() {
        super(ParsingException.class);
    }

    /** Tests {@link ParsingException#ParsingException()}. */
    @Test
    @Override
    public void testNoArgConstructor() throws InstantiationException, IllegalAccessException {
        super.testNoArgConstructor();
    }

    /** Tests {@link ParsingException#ParsingException(String)}. */
    @Test
    @Override
    public void testMessageConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        super.testMessageConstructor();
    }

    /** Tests {@link ParsingException#ParsingException(Throwable)}. */
    @Test
    @Override
    public void testCauseConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        super.testCauseConstructor();
    }

    /** Tests {@link ParsingException#ParsingException(String, Throwable)}. */
    @Test
    @Override
    public void testBothConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        super.testBothConstructor();
    }
}
