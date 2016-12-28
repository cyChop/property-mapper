package org.keyboardplaying.mapper.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.keyboardplaying.mapper.exception.ParsingException;

/**
 * Abstract class providing implementation for tests commons to {@link TemporalParser} implementations.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
@SuppressWarnings("javadoc")
public abstract class TemporalParserTest<T> {

    private static final String DATE_TIME = "1985/10/24-21:20:42";
    private static final String DATE = "2012/06/29";

    private TemporalParser<T> parser;
    private T expectedObject;
    private boolean timestamp;

    TemporalParserTest(TemporalParser<T> parser, T expectedObject, boolean timestamp) {
        this.parser = parser;
        this.expectedObject = expectedObject;
        this.timestamp = timestamp;
    }

    /**
     * Tests the conversion of a date {@link String} to date.
     */
    @Test
    public void testConvertCorrectTimeStringToDateTimestamp() throws ParsingException {
        assertEquals(expectedObject, parser.convertFromString(getExpectedString()));
    }

    /**
     * Tests the failing of the conversion if the supplied string does not match the expected format in case of a
     * timestamp parser.
     */
    @Test
    public void testConvertIncorrectTimeStringToTime() {
        if (timestamp) {
            try {
                parser.convertFromString(DATE);
                fail("A parsing exception should have been thrown.");
            } catch (ParsingException e) {
                // That's OK
            }
        }
    }

    /**
     * Ensures the parsing of a non-date string throws an exception.
     */
    @Test(expected = ParsingException.class)
    public void testConvertNoTimeStringToDate() throws ParsingException {
        parser.convertFromString("notADate");
    }

    /**
     * Tests the parsing of a date to a {@link String}.
     */
    @Test
    public void testConvertDateTimestampToString() throws ParsingException {
        assertEquals(getExpectedString(), parser.convertToString(expectedObject));
    }

    private String getExpectedString() {
        return timestamp ? DATE_TIME : DATE;
    }
}
