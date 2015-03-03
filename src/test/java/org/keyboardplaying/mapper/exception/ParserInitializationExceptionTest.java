package org.keyboardplaying.mapper.exception;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

/**
 * Tests each constructor for {@link ParserInitializationException}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ParserInitializationExceptionTest extends
        MapperExceptionTest<ParserInitializationException> {

    /** Creates a new instance. */
    public ParserInitializationExceptionTest() {
        super(ParserInitializationException.class);
    }

    /** Tests {@link ParserInitializationException#ParserInitializationException()}. */
    @Test
    @Override
    public void testNoArgConstructor() throws InstantiationException, IllegalAccessException {
        super.testNoArgConstructor();
    }

    /** Tests {@link ParserInitializationException#ParserInitializationException(String)}. */
    @Test
    @Override
    public void testMessageConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        super.testMessageConstructor();
    }

    /** Tests {@link ParserInitializationException#ParserInitializationException(Throwable)}. */
    @Test
    @Override
    public void testCauseConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        super.testCauseConstructor();
    }

    /**
     * Tests
     * {@link ParserInitializationException#ParserInitializationException(String, Throwable)}.
     */
    @Test
    @Override
    public void testBothConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        super.testBothConstructor();
    }
}
