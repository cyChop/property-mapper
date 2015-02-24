package org.keyboardplaying.mapper.converter;

import java.util.Date;

import org.keyboardplaying.mapper.exception.ConversionException;

/**
 * Implementation of {@link Converter} for {@code Date} to {@code String} conversion.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class DateConverter extends BaseTemporalConverter<Date> {

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.Converter#convertFromString(java .lang.String)
     *
     * @see org.keyboardplaying.mapper.converter.BaseTemporalConverter#
     * convertStringToDate(java.lang.String)
     */
    @Override
    public Date convertFromString(String value) throws ConversionException {
        return convertStringToDate(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.Converter#convertToString(java.lang .Object)
     *
     * @see org.keyboardplaying.mapper.converter.BaseTemporalConverter#
     * convertDateToString(java.util.Date)
     */
    @Override
    public String convertToString(Date value) {
        return convertDateToString(value);
    }
}
