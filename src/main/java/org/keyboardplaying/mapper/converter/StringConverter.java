package org.keyboardplaying.mapper.converter;

import org.keyboardplaying.mapper.exception.ConversionException;

/**
 * Implementation of {@link Converter} for {@code String} to {@code String} conversion.
 * <p/>
 * No change is made except in case of a default value.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class StringConverter implements Converter<String> {

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.Converter#convertFromString(java .lang.String)
     */
    @Override
    public String convertFromString(String value) throws ConversionException {
        return value;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.Converter#convertToString(java.lang .Object)
     */
    @Override
    public String convertToString(String value) {
        return value;
    }
}
