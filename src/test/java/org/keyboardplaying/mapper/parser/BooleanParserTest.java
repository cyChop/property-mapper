package org.keyboardplaying.mapper.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
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
    private BooleanParser biC = new BooleanParser();

    /** Initializes the languages of the French parser. */
    @Before
    public void init() {
        frC.setTrueFalse(new String[] { Defaults.BOOLEAN_YES_FR }, new String[] { Defaults.BOOLEAN_NO_FR });
        biC.setTrueFalse(new String[] { Defaults.BOOLEAN_YES, Defaults.BOOLEAN_YES_FR },
                new String[] { Defaults.BOOLEAN_NO, Defaults.BOOLEAN_NO_FR });
    }

    /** Ensures {@link BooleanParser#setTrueFalse(String[], String[])} fails if one of the array is null. */
    @Test(expected = IllegalArgumentException.class)
    public void testSetNullArray() {
        enC.setTrueFalse(null, null);
    }

    /** Ensures {@link BooleanParser#setTrueFalse(String[], String[])} fails if one of the array is empty. */
    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyArray() {
        enC.setTrueFalse(new String[0], new String[0]);
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

    /** Tests the conversion to String when the parser has several possibilities. */
    @Test
    public void testConvertBilingualFromString() throws ParsingException {
        assertTrue(biC.convertFromString(Defaults.BOOLEAN_YES));
        assertTrue(biC.convertFromString(Defaults.BOOLEAN_YES_FR));
        assertFalse(biC.convertFromString(Defaults.BOOLEAN_NO));
        assertFalse(biC.convertFromString(Defaults.BOOLEAN_NO_FR));
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

    /** Tests the conversion to String when the parser has several possibilities. */
    @Test
    public void testConvertBilingualToString() {
        assertEquals(Defaults.BOOLEAN_YES, biC.convertToString(true));
        assertEquals(Defaults.BOOLEAN_NO, biC.convertToString(false));
    }
}
