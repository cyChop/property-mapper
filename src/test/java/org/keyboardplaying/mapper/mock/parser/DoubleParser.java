package org.keyboardplaying.mapper.mock.parser;

import org.keyboardplaying.mapper.parser.SimpleParser;
import org.keyboardplaying.mapper.exception.ParsingException;

// XXX Javadoc
/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class DoubleParser implements SimpleParser<Double> {

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.SimpleParser#convertFromString(java .lang.String)
     */
    @Override
    public Double convertFromString(String value) throws ParsingException {
        return Double.parseDouble(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.SimpleParser#convertToString(java.lang .Object)
     */
    @Override
    public String convertToString(Double value) throws ParsingException {
        return value.toString();
    }
}
