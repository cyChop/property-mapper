package org.keyboardplaying.mapper.engine;

import org.keyboardplaying.mapper.exception.ParserInitializationException;
import org.keyboardplaying.mapper.parser.Parser;

/**
 * Provides the correct implementation of {@link Parser} to use based on the type of the field to convert.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public interface ParserProvider {

    /**
     * Fetches the appropriate {@link Parser} for the supplied class.
     *
     * @param klass
     *            the class to convert from or to
     * @return the {@link Parser} to use for parsing
     * @throws ParserInitializationException
     *             if the {@link Parser} cannot be found or initialized
     */
    <T> Parser<? super T> getParser(Class<T> klass) throws ParserInitializationException;
}
