package org.keyboardplaying.mapper.converter;

import java.util.Calendar;

import org.keyboardplaying.mapper.exception.ConversionException;

/**
 * Implementation of {@link Converter} for {@code Calendar} to {@code String} conversion.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class CalendarConverter extends BaseTemporalConverter<Calendar> {

    /**
     * Converts a {@link String} representation of a date to a {@link Calendar} instance.
     * <p/>
     * The {@link String} is expected to conform to the format specified for this
     * {@link BaseTemporalConverter}.
     *
     * @param value
     *            the {@link String} representation of a date
     * @return a {@link Calendar} instance
     * @throws ConversionException
     *             when the supplied {@link String} could not be parsed
     * @see {@link BaseTemporalConverter#convertStringToDate(String)}
     */
    @Override
    public Calendar convertFromString(String value) throws ConversionException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertStringToDate(value));
        return calendar;
    }

    /**
     * Converts a {@link Calendar} instance to a {@link String} representation.
     * <p/>
     * The {@link String} will conform to the format specified for this
     * {@link BaseTemporalConverter}.
     *
     * @param value
     *            the {@link Calendar}
     * @return a {@link String} representation of the supplied date
     * @throws ConversionException
     *             when the supplied {@link String} could not be parsed
     * @see {@link BaseTemporalConverter#convertDateToString(String)}
     */
    @Override
    public String convertToString(Calendar value) {
        return convertDateToString(value.getTime());
    }
}
