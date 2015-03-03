package org.keyboardplaying.mapper.parser;

import java.math.BigInteger;

import org.keyboardplaying.mapper.exception.ParsingException;

/**
 * Implementation of {@link Parser} for {@code BigInteger} to {@code String} parsing.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class BigIntegerParser implements Parser<BigInteger> {

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.Parser#convertFromString(java .lang.String)
     */
    @Override
    public BigInteger convertFromString(String value) throws ParsingException {
        try {
            return new BigInteger(value);
        } catch (IllegalArgumentException e) {
            throw new ParsingException(
                    "Value <" + value + "> could not be parsed to BigInteger", e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.Parser#convertToString(java.lang .Object)
     */
    @Override
    public String convertToString(BigInteger value) {
        return value.toString();
    }
}
