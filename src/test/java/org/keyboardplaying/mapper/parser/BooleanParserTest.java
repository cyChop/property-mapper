package org.keyboardplaying.mapper.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.keyboardplaying.mapper.Defaults;
import org.keyboardplaying.mapper.exception.ParsingException;

/**
 * Test class for {@link BooleanParser}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
@SuppressWarnings("javadoc")
public class BooleanParserTest {

    private BooleanParser enC = new BooleanParser();
    private BooleanParser frC = new BooleanParser();

    {
        frC.setTrueFalse(Defaults.BOOLEAN_YES_FR, Defaults.BOOLEAN_NO_FR);
    }

    /** Tests the conversion from an English Yes/No String. */
    @Test
    public void testConvertEnglishFromEnglishString() throws ParsingException {
        assertTrue(enC.convertFromString(Defaults.BOOLEAN_YES));
        assertFalse(enC.convertFromString(Defaults.BOOLEAN_NO));
    }

    /** Tests the conversion from an English Yes/No String when the input does not match the expectations. */
    @Test(expected = ParsingException.class)
    public void testConvertEnglishFromFrenchString() throws ParsingException {
        enC.convertFromString(Defaults.BOOLEAN_NO_FR);
    }

    /** Tests the conversion from an French Oui/Non String. */
    @Test
    public void testConvertFrenchFromFrenchString() throws ParsingException {
        assertTrue(frC.convertFromString(Defaults.BOOLEAN_YES_FR));
        assertFalse(frC.convertFromString(Defaults.BOOLEAN_NO_FR));
    }

    /** Tests the conversion from an French Oui/Non String when the input does not match the expectations. */
    @Test(expected = ParsingException.class)
    public void testConvertFrenchFromEnglishString() throws ParsingException {
        frC.convertFromString(Defaults.BOOLEAN_YES);
    }

    /** Tests converting to an English Yes/No String. */
    @Test
    public void testConvertToEnglishString() {
        assertEquals(Defaults.BOOLEAN_YES, enC.convertToString(true));
        assertEquals(Defaults.BOOLEAN_NO, enC.convertToString(false));
    }

    /** Tests converting to an French Oui/Non String. */
    @Test
    public void testConvertToFrenchString() {
        assertEquals(Defaults.BOOLEAN_YES_FR, frC.convertToString(true));
        assertEquals(Defaults.BOOLEAN_NO_FR, frC.convertToString(false));
    }
}
