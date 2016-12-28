package org.keyboardplaying.mapper.parser;

import org.keyboardplaying.mapper.exception.ParsingException;

/**
 * Implementation of {@link SimpleParser} for {@code String} to {@code String} parsing.
 * <p/>
 * No change is made except in case of a default value.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class StringParser implements SimpleParser<String> {

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.SimpleParser#convertFromString(java .lang.String)
     */
    @Override
    public String convertFromString(String value) throws ParsingException {
        return value;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.SimpleParser#convertToString(java.lang .Object)
     */
    @Override
    public String convertToString(String value) {
        return value;
    }
}
