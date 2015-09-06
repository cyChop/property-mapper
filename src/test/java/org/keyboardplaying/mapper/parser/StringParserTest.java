package org.keyboardplaying.mapper.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.keyboardplaying.mapper.exception.ParsingException;

/**
 * Tests for the {@link StringParser}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
@SuppressWarnings("javadoc")
public class StringParserTest {

    private StringParser c = new StringParser();

    /** Tests the conversion of a {@link String} in the object to a {@link String} in the map. */
    @Test
    public void testConvertFromString() throws ParsingException {
        assertEquals("value", c.convertFromString("value"));
    }

    /** Tests the conversion of a {@link String} in the map to a {@link String} in the object. */
    @Test
    public void testConvertToString() {
        assertEquals("value", c.convertToString("value"));
    }
}
