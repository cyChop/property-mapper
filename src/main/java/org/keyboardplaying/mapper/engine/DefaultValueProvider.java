package org.keyboardplaying.mapper.engine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility to get the default value of a class.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
final class DefaultValueProvider {

    private boolean bool;
    private byte b;
    private char c;
    private short s;
    private int i;
    private long l;
    private float f;
    private double d;

    private final Map<Class<?>, Object> defaults;

    /**
     * Creates a new instance.
     */
    DefaultValueProvider() {
        Map<Class<?>, Object> values = new HashMap<>();
        values.put(boolean.class, bool);
        values.put(byte.class, b);
        values.put(char.class, c);
        values.put(short.class, s);
        values.put(int.class, i);
        values.put(long.class, l);
        values.put(float.class, f);
        values.put(double.class, d);
        defaults = Collections.unmodifiableMap(values);
    }

    /**
     * Returns the default value of an object based on its type.
     * <p/>
     * A non-null value will only be returned for primitive types.
     *
     * @param klass the type we need the default value of
     * @return the default value for the supplied type
     */
    Object getDefaultValue(Class<?> klass) {
        return defaults.get(klass);
    }
}
