package org.keyboardplaying.mapper.mock.parser;

/**
 * A parser to test the case of a parser without a no-arg constructor.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ArgConstructorParser extends DoubleParser {

    /**
     * Creates a new instance.
     *
     * @param arg
     *            an argument whatsoever
     */
    public ArgConstructorParser(String arg) {
    }
}
