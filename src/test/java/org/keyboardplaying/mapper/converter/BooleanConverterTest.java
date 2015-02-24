package org.keyboardplaying.mapper.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.keyboardplaying.mapper.Defaults;
import org.keyboardplaying.mapper.exception.ConversionException;

// XXX Javadoc
/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class BooleanConverterTest {

    private BooleanConverter enC = new BooleanConverter();
    private BooleanConverter frC = new BooleanConverter();

    {
        frC.setTrueFalse(Defaults.BOOLEAN_YES_FR, Defaults.BOOLEAN_NO_FR);
    }

    @Test
    public void testConvertFromString() throws ConversionException {
        assertTrue(enC.convertFromString(Defaults.BOOLEAN_YES));
        assertFalse(enC.convertFromString(Defaults.BOOLEAN_NO));
        try {
            enC.convertFromString(Defaults.BOOLEAN_NO_FR);
            fail();
        } catch (ConversionException e) {
            // incorrect value
        } catch (Exception e) {
            fail();
        }

        assertTrue(frC.convertFromString(Defaults.BOOLEAN_YES_FR));
        assertFalse(frC.convertFromString(Defaults.BOOLEAN_NO_FR));
        try {
            frC.convertFromString(Defaults.BOOLEAN_YES);
            fail();
        } catch (ConversionException e) {
            // incorrect value
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertToString() throws ConversionException {
        assertEquals(Defaults.BOOLEAN_YES, enC.convertToString(true));
        assertEquals(Defaults.BOOLEAN_NO, enC.convertToString(false));
        assertEquals(Defaults.BOOLEAN_YES_FR, frC.convertToString(true));
        assertEquals(Defaults.BOOLEAN_NO_FR, frC.convertToString(false));
    }

    @Test
    public void testNull() throws ConversionException {
        assertEquals(Defaults.BOOLEAN_NO, enC.convertToString(null));
        assertEquals(Defaults.BOOLEAN_NO_FR, frC.convertToString(null));
    }
}
