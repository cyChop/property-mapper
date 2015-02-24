package org.keyboardplaying.mapper.mock.converter;

/**
 * A converter to test the case of a converter whose no-arg constructor is not accessible.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class PrivateConstructorConverter extends DoubleConverter {

    /** Creates a new instance. */
    private PrivateConstructorConverter() {
    }
}
