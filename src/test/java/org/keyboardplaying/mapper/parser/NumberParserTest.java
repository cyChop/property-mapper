package org.keyboardplaying.mapper.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.Test;
import org.keyboardplaying.mapper.exception.ParsingException;

// XXX Javadoc
/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class NumberParserTest {

    private IntegerParser ic = new IntegerParser();
    private LongParser lc = new LongParser();
    private BigIntegerParser bic = new BigIntegerParser();

    @Test
    public void testConvertStringToInteger() throws ParsingException {
        assertEquals(Integer.valueOf(1), ic.convertFromString("1"));
        try {
            ic.convertFromString("notANumber");
            fail();
        } catch (ParsingException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertIntegerToString() {
        assertEquals("1", ic.convertToString(1));
        assertEquals("1", ic.convertToString(Integer.valueOf(1)));
    }

    @Test
    public void testConvertStringToLong() throws ParsingException {
        assertEquals(Long.valueOf(1), lc.convertFromString("1"));
        try {
            lc.convertFromString("notANumber");
            fail();
        } catch (ParsingException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertLongToString() {
        assertEquals("1", lc.convertToString(1L));
        assertEquals("1", lc.convertToString(Long.valueOf(1)));
    }

    @Test
    public void testConvertStringToBigInteger() throws ParsingException {
        assertEquals(BigInteger.valueOf(1), bic.convertFromString("1"));
        try {
            bic.convertFromString("notANumber");
            fail();
        } catch (ParsingException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertBigIntegerToString() {
        assertEquals("1", bic.convertToString(BigInteger.valueOf(1)));
    }
}
