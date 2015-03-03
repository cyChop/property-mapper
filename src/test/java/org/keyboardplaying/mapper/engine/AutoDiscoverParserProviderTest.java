package org.keyboardplaying.mapper.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.keyboardplaying.mapper.parser.BigIntegerParser;
import org.keyboardplaying.mapper.parser.BooleanParser;
import org.keyboardplaying.mapper.parser.CalendarParser;
import org.keyboardplaying.mapper.parser.IntegerParser;
import org.keyboardplaying.mapper.parser.LongParser;
import org.keyboardplaying.mapper.parser.StringParser;
import org.keyboardplaying.mapper.exception.ParserInitializationException;
import org.keyboardplaying.mapper.mock.parser.DoubleParser;
import org.keyboardplaying.mapper.mock.parser.ExtendedDateParser;

// XXX Javadoc
/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class AutoDiscoverParserProviderTest {

    private AutoDiscoverParserProvider provider;

    @Before
    public void initProvider() {
        provider = AutoDiscoverParserProvider.getInstance();
    }

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

    @Test
    public void testGetSubclassParser() throws ParserInitializationException {
        // you still need to write a descriptor for GregorianCalendar, though
        assertParserClass(CalendarParser.class, GregorianCalendar.class);
    }

    @Test(expected = ParserInitializationException.class)
    public void testGetParserWithoutDescriptor() throws ParserInitializationException {
        // tests at the same time that auto-boxing is of no avail
        // (a bogus java.lang.Character descriptor exists)
        provider.getParser(char.class);
    }

    @Test(expected = ParserInitializationException.class)
    public void testGetParserWithEmptyDescriptor() throws ParserInitializationException {
        provider.getParser(Short.class);
    }

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

    @Test
    public void testGetExtensibleParser() throws ParserInitializationException {
        // for something not existing in original project
        assertParserClass(DoubleParser.class, Double.class);
        // replacing an existing parser
        assertParserClass(ExtendedDateParser.class, Date.class);
    }

    @Test(expected = ParserInitializationException.class)
    public void testGetNonParserDescriptor() throws ParserInitializationException {
        // descriptor file does not link to a Parser
        provider.getParser(Character.class);
    }

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
