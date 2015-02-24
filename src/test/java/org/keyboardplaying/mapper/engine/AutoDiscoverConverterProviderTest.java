package org.keyboardplaying.mapper.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.keyboardplaying.mapper.converter.BigIntegerConverter;
import org.keyboardplaying.mapper.converter.BooleanConverter;
import org.keyboardplaying.mapper.converter.CalendarConverter;
import org.keyboardplaying.mapper.converter.IntegerConverter;
import org.keyboardplaying.mapper.converter.LongConverter;
import org.keyboardplaying.mapper.converter.StringConverter;
import org.keyboardplaying.mapper.exception.ConverterInitializationException;
import org.keyboardplaying.mapper.mock.converter.DoubleConverter;
import org.keyboardplaying.mapper.mock.converter.ExtendedDateConverter;

// XXX Javadoc
/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class AutoDiscoverConverterProviderTest {

    private AutoDiscoverConverterProvider provider;

    @Before
    public void initProvider() {
        provider = AutoDiscoverConverterProvider.getInstance();
    }

    @Test
    public void testGetConverter() throws ConverterInitializationException {
        assertConverterClass(StringConverter.class, String.class);
        assertConverterClass(BooleanConverter.class, Boolean.class);
        assertConverterClass(BooleanConverter.class, boolean.class);
        assertConverterClass(IntegerConverter.class, Integer.class);
        assertConverterClass(IntegerConverter.class, int.class);
        assertConverterClass(LongConverter.class, Long.class);
        assertConverterClass(LongConverter.class, long.class);
        assertConverterClass(BigIntegerConverter.class, BigInteger.class);
        // assertConverterClass(DateConverter.class, Date.class);
        assertConverterClass(CalendarConverter.class, Calendar.class);
    }

    @Test
    public void testGetSubclassConverter() throws ConverterInitializationException {
        // you still need to write a descriptor for GregorianCalendar, though
        assertConverterClass(CalendarConverter.class, GregorianCalendar.class);
    }

    @Test(expected = ConverterInitializationException.class)
    public void testGetConverterWithoutDescriptor() throws ConverterInitializationException {
        // tests at the same time that auto-boxing is of no avail
        // (a bogus java.lang.Character descriptor exists)
        provider.getConverter(char.class);
    }

    @Test(expected = ConverterInitializationException.class)
    public void testGetConverterWithEmptyDescriptor() throws ConverterInitializationException {
        provider.getConverter(Short.class);
    }

    @Test(expected = InstantiationException.class)
    public void testGetConverterWithArgumentedConstructor() throws Throwable {
        try {
            provider.getConverter(byte.class);
        } catch (ConverterInitializationException e) {
            assertNotNull(e.getCause());
            throw e.getCause();
        }
        fail("A ConverterInitializationException was expected.");
    }

    @Test(expected = IllegalAccessException.class)
    public void testGetConverterWithPrivateConstructor() throws Throwable {
        try {
            provider.getConverter(short.class);
        } catch (ConverterInitializationException e) {
            assertNotNull(e.getCause());
            throw e.getCause();
        }
        fail("A ConverterInitializationException was expected.");
    }

    @Test
    public void testGetExtensibleConverter() throws ConverterInitializationException {
        // for something not existing in original project
        assertConverterClass(DoubleConverter.class, Double.class);
        // replacing an existing converter
        assertConverterClass(ExtendedDateConverter.class, Date.class);
    }

    @Test(expected = ConverterInitializationException.class)
    public void testGetNonConverterDescriptor() throws ConverterInitializationException {
        // descriptor file does not link to a Converter
        provider.getConverter(Character.class);
    }

    @Test(expected = ConverterInitializationException.class)
    public void testGetNonExistingConverterDescriptor() throws ConverterInitializationException {
        // descriptor file links to a non-existent class
        provider.getConverter(Byte.class);
    }

    private void assertConverterClass(Class<?> expectedConverterClass, Class<?> fieldClass)
            throws ConverterInitializationException {
        assertEquals(expectedConverterClass, provider.getConverter(fieldClass).getClass());
    }
}
