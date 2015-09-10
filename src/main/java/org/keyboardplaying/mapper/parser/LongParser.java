package org.keyboardplaying.mapper.parser;

import org.keyboardplaying.mapper.exception.ParsingException;

/**
 * Implementation of {@link SimpleParser} for {@code Long} to {@code String} parsing.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class LongParser implements SimpleParser<Long> {

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.SimpleParser#convertFromString(java .lang.String)
     */
    @Override
    public Long convertFromString(String value) throws ParsingException {
        try {
            return Long.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new ParsingException("Value <" + value + "> could not be parsed to long", e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.SimpleParser#convertToString(java.lang .Object)
     */
    @Override
    public String convertToString(Long value) {
        return value.toString();
    }
}
