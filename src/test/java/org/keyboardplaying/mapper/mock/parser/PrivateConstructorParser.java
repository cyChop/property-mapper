package org.keyboardplaying.mapper.mock.parser;

/**
 * A parser to test the case of a parser whose no-arg constructor is not accessible.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class PrivateConstructorParser extends DoubleParser {

    /**
     * Creates a new instance.
     */
    private PrivateConstructorParser() {
    }
}
