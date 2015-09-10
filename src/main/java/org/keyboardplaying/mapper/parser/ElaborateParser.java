package org.keyboardplaying.mapper.parser;

import java.util.Map;

import org.keyboardplaying.mapper.engine.ParserProvider;
import org.keyboardplaying.mapper.exception.ParsingException;

/**
 * This interface is to be used when the {@link SimpleParser} does not allow for sufficient functionality (e.g. when
 * several fields have to be used for parsing).
 * <p/>
 * <strong>Notice:</strong> Please note that all implementations of this interface should provide a {@code public}
 * no-arg constructor, so that the {@link ParserProvider} is able to instantiate any {@link ElaborateParser}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 * @param <F>
 *            the type of field this parser parses from and to
 */
public interface ElaborateParser<F> {

    /**
     * An {@link ElaborateParser} when the {@link SimpleParser} should be used.
     *
     * @author Cyrille Chopelet (http://keyboardplaying.org)
     */
    static class None implements ElaborateParser<Object> {

        /*
         * (non-Javadoc)
         *
         * @see org.keyboardplaying.mapper.parser.ElaborateParser#fromMap(java.util.Map)
         */
        @Override
        public Object fromMap(Map<String, String> map) throws ParsingException {
            throw new UnsupportedOperationException("This should never be called.");
        }

        /*
         * (non-Javadoc)
         *
         * @see org.keyboardplaying.mapper.parser.ElaborateParser#toMap(java.lang.Object, java.util.Map)
         */
        @Override
        public void toMap(Object value, Map<String, String> map) throws ParsingException {
            throw new UnsupportedOperationException("This should never be called.");
        }
    }

    /**
     * Parses an object from the {@code Map}.
     * <p/>
     * Implementations do not need to be null-safe.
     *
     * @param map
     *            the map containing the serialized objects
     * @return the deserialized object
     * @throws ParsingException
     *             if the parsing cannot be performed
     */
    F fromMap(Map<String, String> map) throws ParsingException;

    /**
     * Parses an object to its {@link String} representation into the map.
     * <p/>
     * Implementations <strong>must be</strong> null-safe.
     *
     * @param value
     *            the unserialized object
     * @param map
     *            the map containing the serialized objects
     * @throws ParsingException
     *             if the parsing cannot be performed
     */
    void toMap(F value, Map<String, String> map) throws ParsingException;
}
