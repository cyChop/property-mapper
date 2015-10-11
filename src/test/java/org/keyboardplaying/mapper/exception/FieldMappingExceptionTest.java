package org.keyboardplaying.mapper.exception;

import java.lang.reflect.Field;

import org.junit.Test;

/**
 * Tests each constructor for {@link FieldMappingException}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
@SuppressWarnings("javadoc")
public class FieldMappingExceptionTest extends ExceptionTest {

    private static final String FIELD_PREFIX = "[org.keyboardplaying.mapper.exception.FieldMappingExceptionTest.test] ";

    @SuppressWarnings("unused")
    private String test;

    private Field getField() throws NoSuchFieldException, SecurityException {
        return this.getClass().getDeclaredField("test");
    }

    /** Tests {@link FieldMappingException#FieldMappingException(Field)}. */
    @Test
    public void testNoArgConstructor() throws NoSuchFieldException, SecurityException {
        FieldMappingException exception = new FieldMappingException(getField());
        assertMessageOnly(FIELD_PREFIX, exception);
    }

    /** Tests {@link FieldMappingException#FieldMappingException(Field, String)}. */
    @Test
    public void testMessageConstructor() throws IllegalArgumentException, SecurityException, NoSuchFieldException {
        String message = "Hello, world!";
        FieldMappingException exception = new FieldMappingException(getField(), message);
        assertMessageOnly(FIELD_PREFIX + message, exception);
    }

    /** Tests {@link FieldMappingException#FieldMappingException(Field, Throwable)}. */
    @Test
    public void testCauseConstructor() throws IllegalArgumentException, SecurityException, NoSuchFieldException {
        Throwable cause = new IllegalArgumentException("He's dead, Jim!");
        FieldMappingException exception = new FieldMappingException(getField(), cause);
        assertMessageAndCause(FIELD_PREFIX + cause.getMessage(), cause, exception);
    }

    /** Tests {@link FieldMappingException#FieldMappingException(Field, String, Throwable)}. */
    @Test
    public void testBothConstructor() throws IllegalArgumentException, SecurityException, NoSuchFieldException {
        String message = "Hello, world!";
        Throwable cause = new IllegalArgumentException("He's dead, Jim!");
        FieldMappingException exception = new FieldMappingException(getField(), message, cause);
        assertMessageAndCause(FIELD_PREFIX + message, cause, exception);
    }
}
