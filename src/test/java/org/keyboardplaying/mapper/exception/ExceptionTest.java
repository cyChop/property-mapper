package org.keyboardplaying.mapper.exception;

import static org.junit.Assert.assertEquals;

/**
 * Utility methods when testing Exceptions.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
abstract class ExceptionTest {

    protected void assertNoMessageNorCause(Exception exception) {
        assertMessageAndCause(null, null, exception);
    }

    protected void assertMessageOnly(String message, Exception exception) {
        assertMessageAndCause(message, null, exception);
    }

    protected void assertCauseOnly(Throwable cause, Exception exception) {
        assertMessageAndCause(cause.toString(), cause, exception);
    }

    protected void assertMessageAndCause(String message, Throwable cause, Exception exception) {
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
