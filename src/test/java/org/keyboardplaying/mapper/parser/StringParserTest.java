package org.keyboardplaying.mapper.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.keyboardplaying.mapper.exception.ParsingException;

// XXX Javadoc
/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class StringParserTest {

    private StringParser c = new StringParser();

    @Test
    public void testConvertFromString() throws ParsingException {
        assertEquals("value", c.convertFromString("value"));
    }

    @Test
    public void testConvertToString() {
        assertEquals("value", c.convertToString("value"));
    }
}
