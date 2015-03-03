package org.keyboardplaying.mapper.parser;

import java.util.Date;

import org.keyboardplaying.mapper.exception.ParsingException;

/**
 * Implementation of {@link Parser} for {@code Date} to {@code String} parsing.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class DateParser extends BaseTemporalParser<Date> {

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.Parser#convertFromString(java .lang.String)
     *
     * @see org.keyboardplaying.mapper.parser.BaseTemporalParser#
     * convertStringToDate(java.lang.String)
     */
    @Override
    public Date convertFromString(String value) throws ParsingException {
        return convertStringToDate(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.Parser#convertToString(java.lang .Object)
     *
     * @see org.keyboardplaying.mapper.parser.BaseTemporalParser#
     * convertDateToString(java.util.Date)
     */
    @Override
    public String convertToString(Date value) {
        return convertDateToString(value);
    }
}
