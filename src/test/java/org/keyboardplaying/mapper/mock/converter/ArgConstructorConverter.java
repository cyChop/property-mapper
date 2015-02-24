package org.keyboardplaying.mapper.mock.converter;

/**
 * A converter to test the case of a converter without a no-arg constructor.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ArgConstructorConverter extends DoubleConverter {

    /**
     * Creates a new instance.
     *
     * @param arg
     *            an argument whatsoever
     */
    public ArgConstructorConverter(String arg) {
    }
}
