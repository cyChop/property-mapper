package org.keyboardplaying.mapper.engine;

import org.keyboardplaying.mapper.exception.ParserInitializationException;
import org.keyboardplaying.mapper.parser.SimpleParser;

/**
 * Provides the correct implementation of {@link SimpleParser} to use based on the type of the field to convert.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public interface ParserProvider {

    /**
     * Fetches the appropriate {@link SimpleParser} for the supplied class.
     *
     * @param klass
     *            the class to convert from or to
     * @return the {@link SimpleParser} to use for parsing
     * @throws ParserInitializationException
     *             if the {@link SimpleParser} cannot be found or initialized
     */
    <T> SimpleParser<? super T> getParser(Class<T> klass) throws ParserInitializationException;
}
