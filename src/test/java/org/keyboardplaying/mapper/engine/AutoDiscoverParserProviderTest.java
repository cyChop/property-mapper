package org.keyboardplaying.mapper.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.keyboardplaying.mapper.exception.ParserInitializationException;
import org.keyboardplaying.mapper.mock.parser.DoubleParser;
import org.keyboardplaying.mapper.mock.parser.ExtendedDateParser;
import org.keyboardplaying.mapper.parser.BigIntegerParser;
import org.keyboardplaying.mapper.parser.BooleanParser;
import org.keyboardplaying.mapper.parser.CalendarParser;
import org.keyboardplaying.mapper.parser.IntegerParser;
import org.keyboardplaying.mapper.parser.LongParser;
import org.keyboardplaying.mapper.parser.StringParser;

/**
 * Tests for the {@link AutoDiscoverParserProvider}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
@SuppressWarnings("javadoc")
public class AutoDiscoverParserProviderTest {

    private AutoDiscoverParserProvider provider = AutoDiscoverParserProvider.getInstance();

    /** Tests the fetching of standard parsers. */
    @Test
    public void testGetParser() throws ParserInitializationException {
        assertParserClass(StringParser.class, String.class);
        assertParserClass(BooleanParser.class, Boolean.class);
        assertParserClass(BooleanParser.class, boolean.class);
        assertParserClass(IntegerParser.class, Integer.class);
        assertParserClass(IntegerParser.class, int.class);
        assertParserClass(LongParser.class, Long.class);
        assertParserClass(LongParser.class, long.class);
        assertParserClass(BigIntegerParser.class, BigInteger.class);
        // assertParserClass(DateParser.class, Date.class);
        assertParserClass(CalendarParser.class, Calendar.class);
    }

    /**
     * Tests the fetching of a standard parser for a subclass.
     * <p/>
     * The descriptor file must be defined for the subclass.
     */
    @Test
    public void testGetSubclassParser() throws ParserInitializationException {
        // you still need to write a descriptor for GregorianCalendar, though
        assertParserClass(CalendarParser.class, GregorianCalendar.class);
    }

    /**
     * Tests the fetching of a Parser without a descriptor file.
     * <p/>
     * The tested type is {@code char}. A descriptor exists for {@link java.lang.Character}.
     */
    @Test(expected = ParserInitializationException.class)
    public void testGetParserWithoutDescriptor() throws ParserInitializationException {
        provider.getParser(char.class);
    }

    /** Tests the fetching of a Parser when the descriptor file is empty. */
    @Test(expected = ParserInitializationException.class)
    public void testGetParserWithEmptyDescriptor() throws ParserInitializationException {
        provider.getParser(Short.class);
    }

    /** Tests the fetching of the parser when no no-arg constructor is available. */
    @Test(expected = InstantiationException.class)
    public void testGetParserWithArgumentedConstructor() throws Throwable {
        try {
            provider.getParser(byte.class);
        } catch (ParserInitializationException e) {
            assertNotNull(e.getCause());
            throw e.getCause();
        }
        fail("A ParserInitializationException was expected.");
    }

    /** Tests the fetching of a Parser when the constructor is private. */
    @Test(expected = IllegalAccessException.class)
    public void testGetParserWithPrivateConstructor() throws Throwable {
        try {
            provider.getParser(short.class);
        } catch (ParserInitializationException e) {
            assertNotNull(e.getCause());
            throw e.getCause();
        }
        fail("A ParserInitializationException was expected.");
    }

    /** Tests the fetching of a Parser defined within using project. */
    @Test
    public void testGetExtensibleParser() throws ParserInitializationException {
        assertParserClass(DoubleParser.class, Double.class);
    }

    /** Tests the fetching of a Parser for a class whose descriptor overrides one defined by default. */
    @Test
    public void testDescriptorOverriding() throws ParserInitializationException {
        assertParserClass(ExtendedDateParser.class, Date.class);
    }

    /** Tests the fetching of a Parser when the class in the descriptor is not a parser. */
    @Test(expected = ParserInitializationException.class)
    public void testGetNonParserDescriptor() throws ParserInitializationException {
        // descriptor file does not link to a Parser
        provider.getParser(Character.class);
    }

    /** Tests the fetching of a Parser when the class in descriptor does not exist. */
    @Test(expected = ParserInitializationException.class)
    public void testGetNonExistingParserDescriptor() throws ParserInitializationException {
        // descriptor file links to a non-existent class
        provider.getParser(Byte.class);
    }

    private void assertParserClass(Class<?> expectedParserClass, Class<?> fieldClass)
            throws ParserInitializationException {
        assertEquals(expectedParserClass, provider.getParser(fieldClass).getClass());
    }
}
