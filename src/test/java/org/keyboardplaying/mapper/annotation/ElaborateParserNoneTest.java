package org.keyboardplaying.mapper.annotation;

import java.util.HashMap;

import org.junit.Test;
import org.keyboardplaying.mapper.exception.ParsingException;
import org.keyboardplaying.mapper.parser.ElaborateParser;
import org.keyboardplaying.mapper.parser.ElaborateParser.None;

/**
 * Tests the {@link None} {@link ElaborateParser}.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
@SuppressWarnings("javadoc")
public class ElaborateParserNoneTest {

    private ElaborateParser<Object> parser = new None();

    /**
     * Tests the unmapping with a {@link None} fails.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testFromMap() throws ParsingException {
        parser.fromMap(new HashMap<String, String>());
    }

    /**
     * Tests the mapping with a {@link None} fails.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testToMap() throws ParsingException {
        parser.toMap("hello", new HashMap<String, String>());
    }
}
