package org.keyboardplaying.mapper.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.keyboardplaying.mapper.exception.ConversionException;

// XXX Javadoc
/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class StringConverterTest {

    private StringConverter c = new StringConverter();

    @Test
    public void testConvertFromString() throws ConversionException {
        assertEquals("value", c.convertFromString("value"));
    }

    @Test
    public void testConvertToString() throws ConversionException {
        assertEquals("value", c.convertToString("value"));
    }
}
