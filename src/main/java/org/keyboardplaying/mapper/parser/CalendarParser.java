package org.keyboardplaying.mapper.parser;

import java.util.Calendar;

import org.keyboardplaying.mapper.exception.ParsingException;

/**
 * Implementation of {@link SimpleParser} for {@code Calendar} to {@code String} parsing.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class CalendarParser extends BaseTemporalParser<Calendar> {

    /**
     * Converts a {@link String} representation of a date to a {@link Calendar} instance.
     * <p/>
     * The {@link String} is expected to conform to the format specified for this {@link BaseTemporalParser}.
     *
     * @param value
     *            the {@link String} representation of a date
     * @return a {@link Calendar} instance
     * @throws ParsingException
     *             if the supplied {@link String} could not be parsed
     */
    /* @see BaseTemporalParser#convertStringToDate(String) */
    @Override
    public Calendar convertFromString(String value) throws ParsingException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertStringToDate(value));
        return calendar;
    }

    /**
     * Converts a {@link Calendar} instance to a {@link String} representation.
     * <p/>
     * The {@link String} will conform to the format specified for this {@link BaseTemporalParser}.
     *
     * @param value
     *            the {@link Calendar}
     * @return a {@link String} representation of the supplied date
     */
    /* @see BaseTemporalParser#convertDateToString(String) */
    @Override
    public String convertToString(Calendar value) {
        return convertDateToString(value.getTime());
    }
}
