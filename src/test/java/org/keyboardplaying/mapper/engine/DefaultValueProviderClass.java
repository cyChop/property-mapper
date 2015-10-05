package org.keyboardplaying.mapper.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Tests for the {@link DefaultValueProvider}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class DefaultValueProviderClass {

    private DefaultValueProvider provider = new DefaultValueProvider();

    /** Ensures the default value for a primitive is correct. */
    @Test
    public void testGetDefaultValueForPrimitive() {
        assertEquals(Boolean.FALSE, provider.getDefaultValue(boolean.class));
        assertEquals(Byte.valueOf((byte) 0), provider.getDefaultValue(byte.class));
        assertEquals(Character.valueOf('\u0000'), provider.getDefaultValue(char.class));
        assertEquals(Short.valueOf((short) 0), provider.getDefaultValue(short.class));
        assertEquals(Integer.valueOf(0), provider.getDefaultValue(int.class));
        assertEquals(Long.valueOf(0), provider.getDefaultValue(long.class));
        assertEquals(Float.valueOf(0F), provider.getDefaultValue(float.class));
        assertEquals(Double.valueOf(0.), provider.getDefaultValue(double.class));

        // no autoboxing
        assertNull(provider.getDefaultValue(Integer.class));
    }

    /** Ensures the default value for an object is null. */
    @Test
    public void testGetDefaultValueForObject() {
        assertNull(provider.getDefaultValue(String.class));
        assertNull(provider.getDefaultValue(Object.class));
    }
}
