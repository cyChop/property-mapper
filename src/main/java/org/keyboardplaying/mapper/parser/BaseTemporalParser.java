package org.keyboardplaying.mapper.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.keyboardplaying.mapper.exception.ParsingException;

/**
 * This implementation of {@link SimpleParser} provides some utility methods when parsing time representations.
 * <p/>
 * Using this implies to use {@link Date} objects at least as intermediary steps. You might wish to use more direct ways
 * if it is possible.
 *
 * @param <T> the type of time objects this {@link SimpleParser} converts from and to
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public abstract class BaseTemporalParser<T> implements TemporalParser<T> {

    /**
     * The format to use for the string representation of timestamps.
     */
    private DateFormat format;

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.TemporalParser#setFormat(java .lang.String)
     */
    @Override
    public void setFormat(String format) {
        this.format = new SimpleDateFormat(format);
    }

    /**
     * Converts a {@link String} representation of a date to a {@link Date} instance.
     * <p/>
     * The {@link String} is expected to conform to the format specified for this {@link BaseTemporalParser}.
     *
     * @param value the {@link String} representation of a date
     * @return a {@link Date} instance
     * @throws ParsingException if the supplied {@link String} could not be parsed
     */
    protected Date convertStringToDate(String value) throws ParsingException {
        try {
            return format.parse(value);
        } catch (ParseException e) {
            throw new ParsingException(
                    "Value <" + value + "> could not be parsed to date using format <" + format + ">.");
        }
    }

    /**
     * Converts a {@link Date} instance to a {@link String} representation.
     * <p/>
     * The {@link String} will conform to the format specified for this {@link BaseTemporalParser}.
     *
     * @param value the {@link Date}
     * @return a {@link String} representation of the supplied date
     */
    protected String convertDateToString(Date value) {
        return format.format(value);
    }
}
