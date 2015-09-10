package org.keyboardplaying.mapper.parser;

import org.keyboardplaying.mapper.exception.ParsingException;

/**
 * Implementation of {@link SimpleParser} for {@code Integer} to {@code String} parsing.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class IntegerParser implements SimpleParser<Integer> {

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.SimpleParser#convertFromString(java .lang.String)
     */
    @Override
    public Integer convertFromString(String value) throws ParsingException {
        try {
            return Integer.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new ParsingException("Value <" + value + "> could not be parsed to integer", e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.SimpleParser#convertToString(java.lang .Object)
     */
    @Override
    public String convertToString(Integer value) {
        return value.toString();
    }
}
