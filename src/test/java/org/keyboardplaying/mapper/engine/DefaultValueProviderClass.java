package org.keyboardplaying.mapper.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Tests for the {@link DefaultValueProvider}.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class DefaultValueProviderClass {

    private DefaultValueProvider provider = new DefaultValueProvider();

    /**
     * Ensures the default value for a primitive is correct.
     */
    @Test
    public void testGetDefaultValueForPrimitive() {
        assertEquals(Boolean.FALSE, provider.getDefaultValue(boolean.class));
        assertEquals((byte) 0, provider.getDefaultValue(byte.class));
        assertEquals('\u0000', provider.getDefaultValue(char.class));
        assertEquals((short) 0, provider.getDefaultValue(short.class));
        assertEquals(0, provider.getDefaultValue(int.class));
        assertEquals((long) 0, provider.getDefaultValue(long.class));
        assertEquals(0F, provider.getDefaultValue(float.class));
        assertEquals(0., provider.getDefaultValue(double.class));

        // no autoboxing
        assertNull(provider.getDefaultValue(Integer.class));
    }

    /**
     * Ensures the default value for an object is null.
     */
    @Test
    public void testGetDefaultValueForObject() {
        assertNull(provider.getDefaultValue(String.class));
        assertNull(provider.getDefaultValue(Object.class));
    }
}
