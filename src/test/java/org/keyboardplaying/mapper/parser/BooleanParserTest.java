package org.keyboardplaying.mapper.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.keyboardplaying.mapper.Defaults;
import org.keyboardplaying.mapper.exception.ParsingException;

// XXX Javadoc
/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class BooleanParserTest {

    private BooleanParser enC = new BooleanParser();
    private BooleanParser frC = new BooleanParser();

    {
        frC.setTrueFalse(Defaults.BOOLEAN_YES_FR, Defaults.BOOLEAN_NO_FR);
    }

    @Test
    public void testConvertFromString() throws ParsingException {
        assertTrue(enC.convertFromString(Defaults.BOOLEAN_YES));
        assertFalse(enC.convertFromString(Defaults.BOOLEAN_NO));
        try {
            enC.convertFromString(Defaults.BOOLEAN_NO_FR);
            fail();
        } catch (ParsingException e) {
            // incorrect value
        } catch (Exception e) {
            fail();
        }

        assertTrue(frC.convertFromString(Defaults.BOOLEAN_YES_FR));
        assertFalse(frC.convertFromString(Defaults.BOOLEAN_NO_FR));
        try {
            frC.convertFromString(Defaults.BOOLEAN_YES);
            fail();
        } catch (ParsingException e) {
            // incorrect value
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertToString() throws ParsingException {
        assertEquals(Defaults.BOOLEAN_YES, enC.convertToString(true));
        assertEquals(Defaults.BOOLEAN_NO, enC.convertToString(false));
        assertEquals(Defaults.BOOLEAN_YES_FR, frC.convertToString(true));
        assertEquals(Defaults.BOOLEAN_NO_FR, frC.convertToString(false));
    }

    @Test
    public void testNull() throws ParsingException {
        assertEquals(Defaults.BOOLEAN_NO, enC.convertToString(null));
        assertEquals(Defaults.BOOLEAN_NO_FR, frC.convertToString(null));
    }
}
