package org.keyboardplaying.mapper.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.Test;
import org.keyboardplaying.mapper.exception.ConversionException;

// XXX Javadoc
/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class NumberConverterTest {

    private IntegerConverter ic = new IntegerConverter();
    private LongConverter lc = new LongConverter();
    private BigIntegerConverter bic = new BigIntegerConverter();

    @Test
    public void testConvertStringToInteger() throws ConversionException {
        assertEquals(Integer.valueOf(1), ic.convertFromString("1"));
        try {
            ic.convertFromString("notANumber");
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertIntegerToString() throws ConversionException {
        assertEquals("1", ic.convertToString(1));
        assertEquals("1", ic.convertToString(Integer.valueOf(1)));
    }

    @Test
    public void testConvertStringToLong() throws ConversionException {
        assertEquals(Long.valueOf(1), lc.convertFromString("1"));
        try {
            lc.convertFromString("notANumber");
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertLongToString() throws ConversionException {
        assertEquals("1", lc.convertToString(1L));
        assertEquals("1", lc.convertToString(Long.valueOf(1)));
    }

    @Test
    public void testConvertStringToBigInteger() throws ConversionException {
        assertEquals(BigInteger.valueOf(1), bic.convertFromString("1"));
        try {
            bic.convertFromString("notANumber");
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertBigIntegerToString() throws ConversionException {
        assertEquals("1", bic.convertToString(BigInteger.valueOf(1)));
    }
}
