package org.keyboardplaying.mapper.exception;

/**
 * An exception to throw when a Parser could not be found or initialized.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ParserInitializationException extends MapperException {

    /** Generated serial version UID. */
    private static final long serialVersionUID = 5870298776718721250L;

    /**
     * Constructs a new exception with {@code null} as its detail message. The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause(Throwable)}.
     */
    public ParserInitializationException() {
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
    public ParserInitializationException(String message) {
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
    public ParserInitializationException(Throwable cause) {
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
    public ParserInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
