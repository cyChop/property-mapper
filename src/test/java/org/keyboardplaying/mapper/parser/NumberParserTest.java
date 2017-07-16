package org.keyboardplaying.mapper.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.keyboardplaying.mapper.exception.ParsingException;

/**
 * Abstract class providing implementation for tests commons to {@link TemporalParser} implementations.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
@SuppressWarnings("javadoc")
public abstract class NumberParserTest<T extends Number> {

    private final SimpleParser<T> parser;

    /**
     * Creates a new instance.
     *
     * @param parser the parser to test
     */
    protected NumberParserTest(SimpleParser<T> parser) {
        this.parser = parser;
    }

    protected abstract T getValue(int number);

    /**
     * Tests the conversion of a {@link String} to a {@link Number}.
     */
    @Test(expected = ParsingException.class)
    public void testConvertStringToNumber() throws ParsingException {
        // Correct String
        assertEquals(getValue(42), parser.convertFromString("42"));

        // Incorrect String
        parser.convertFromString("notANumber");
    }

    /**
     * Tests the conversion of a {@link Number} to a {@link String}.
     */
    @Test
    public void testConvertNumberToString() throws ParsingException {
        assertEquals("1337", parser.convertToString(getValue(1337)));
    }
}
