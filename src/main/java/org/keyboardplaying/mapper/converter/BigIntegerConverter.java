package org.keyboardplaying.mapper.converter;

import java.math.BigInteger;

import org.keyboardplaying.mapper.exception.ConversionException;

/**
 * Implementation of {@link Converter} for {@code BigInteger} to {@code String} conversion.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class BigIntegerConverter implements Converter<BigInteger> {

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.Converter#convertFromString(java .lang.String)
     */
    @Override
    public BigInteger convertFromString(String value) throws ConversionException {
        try {
            return new BigInteger(value);
        } catch (IllegalArgumentException e) {
            throw new ConversionException(
                    "Value <" + value + "> could not be parsed to BigInteger", e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.Converter#convertToString(java.lang .Object)
     */
    @Override
    public String convertToString(BigInteger value) {
        return value.toString();
    }
}
