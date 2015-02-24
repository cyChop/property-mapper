package org.keyboardplaying.mapper.exception;

/**
 * An exception to throw when the metadata could not be mapped to the destination object.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class MappingException extends MapperException {

    /** Generated serial version UID. */
    private static final long serialVersionUID = 4272293522867341346L;

    /**
     * Constructs a new exception with {@code null} as its detail message. The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause(Throwable)}.
     */
    public MappingException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message. The cause is not initialized,
     * and may subsequently be initialized by a call to {@link #initCause(Throwable)}.
     *
     * @param message
     *            the detail message. The detail message is saved for later retrieval by the
     *            {@link #getMessage()} method.
     */
    public MappingException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of
     * {@code (cause==null ? null : cause.toString())} (which typically contains the class and
     * detail message of cause). This constructor is useful for exceptions that are little more than
     * wrappers for other throwables.
     *
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *            (A null value is permitted, and indicates that the cause is nonexistent or
     *            unknown.)
     */
    public MappingException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * Note that the detail message associated with cause is not automatically incorporated in this
     * exception's detail message.
     *
     *
     * @param message
     *            the detail message. The detail message is saved for later retrieval by the
     *            {@link #getMessage()} method.
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *            (A null value is permitted, and indicates that the cause is nonexistent or
     *            unknown.)
     */
    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
