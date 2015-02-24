package org.keyboardplaying.mapper.converter;

import org.keyboardplaying.mapper.exception.ConversionException;

/**
 * Implementation of {@link Converter} for {@code Long} to {@code String} conversion.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class LongConverter implements Converter<Long> {

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.Converter#convertFromString(java .lang.String)
     */
    @Override
    public Long convertFromString(String value) throws ConversionException {
        try {
            return Long.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new ConversionException("Value <" + value + "> could not be parsed to long", e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.Converter#convertToString(java.lang .Object)
     */
    @Override
    public String convertToString(Long value) {
        return value.toString();
    }
}
