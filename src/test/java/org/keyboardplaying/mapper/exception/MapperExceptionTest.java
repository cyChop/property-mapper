package org.keyboardplaying.mapper.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.InvocationTargetException;

/**
 * A test class to test the project's exceptions' constructors work as intended.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
abstract class MapperExceptionTest<T extends MapperException> {

    private Class<T> klass;

    protected MapperExceptionTest(Class<T> klass) {
        this.klass = klass;
    }

    protected void testNoArgConstructor() throws InstantiationException, IllegalAccessException {
        T exception = klass.newInstance();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    protected void testMessageConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        String message = "Hello, world!";
        T exception = klass.getConstructor(String.class).newInstance(message);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    protected void testCauseConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        Throwable cause = new IllegalArgumentException("He's dead, Jim!");
        T exception = klass.getConstructor(Throwable.class).newInstance(cause);
        assertEquals(cause.toString(), exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    protected void testBothConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        String message = "Hello, world!";
        Throwable cause = new IllegalArgumentException("He's dead, Jim!");
        T exception = klass.getConstructor(String.class, Throwable.class).newInstance(message,
                cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
