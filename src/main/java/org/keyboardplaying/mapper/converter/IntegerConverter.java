package org.keyboardplaying.mapper.converter;

import org.keyboardplaying.mapper.exception.ConversionException;

/**
 * Implementation of {@link Converter} for {@code Integer} to {@code String} conversion.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class IntegerConverter implements Converter<Integer> {

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.Converter#convertFromString(java .lang.String)
     */
    @Override
    public Integer convertFromString(String value) throws ConversionException {
        try {
            return Integer.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new ConversionException("Value <" + value + "> could not be parsed to integer", e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.Converter#convertToString(java.lang .Object)
     */
    @Override
    public String convertToString(Integer value) {
        return value.toString();
    }
}
