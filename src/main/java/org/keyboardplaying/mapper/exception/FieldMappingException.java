package org.keyboardplaying.mapper.exception;

import java.lang.reflect.Field;

/**
 * A {@link MappingException} prepending the error message with field information.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class FieldMappingException extends MappingException {

    /** Generated serial version UID. */
    private static final long serialVersionUID = -7958092515600140512L;

    /**
     * Constructs a new exception with field information as its detail message. The cause is not initialized, and may
     * subsequently be initialized by a call to {@link #initCause(Throwable)}.
     *
     * @param field
     *            the field concerned with the error
     */
    public FieldMappingException(Field field) {
        this(field, "");
    }

    /**
     * Constructs a new exception with the specified detail message. The cause is not initialized, and may subsequently
     * be initialized by a call to {@link #initCause(Throwable)}.
     *
     * @param field
     *            the field concerned with the error
     * @param message
     *            the detail message. The detail message is saved for later retrieval by the {@link #getMessage()}
     *            method.
     */
    public FieldMappingException(Field field, String message) {
        super(makeMessage(field, message));
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of
     * {@code (cause==null ? null : cause.toString())} (which typically contains the class and detail message of cause).
     * This constructor is useful for exceptions that are little more than wrappers for other throwables.
     *
     * @param field
     *            the field concerned with the error
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A null value is
     *            permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public FieldMappingException(Field field, Throwable cause) {
        this(field, cause.getMessage(), cause);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * <p/>
     * Note that the detail message associated with cause is not automatically incorporated in this exception's detail
     * message.
     *
     *
     * @param field
     *            the field concerned with the error
     * @param message
     *            the detail message. The detail message is saved for later retrieval by the {@link #getMessage()}
     *            method.
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A null value is
     *            permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public FieldMappingException(Field field, String message, Throwable cause) {
        super(makeMessage(field, message), cause);
    }

    private static String makeMessage(Field field, String message) {
        return String.format("[%s.%s] %s", field.getDeclaringClass().getName(), field.getName(), message);
    }
}
