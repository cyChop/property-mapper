package org.keyboardplaying.mapper.engine;

import org.keyboardplaying.mapper.converter.Converter;
import org.keyboardplaying.mapper.exception.ConverterInitializationException;

/**
 * Provides the correct implementation of {@link Converter} to use based on the type of the field to
 * convert.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public interface ConverterProvider {

    /**
     * Fetches the appropriate {@link Converter} for the supplied class.
     *
     * @param klass
     *            the class to convert from or to
     * @return the {@link Converter} to use for conversion
     * @throws ConverterInitializationException
     *             if the {@link Converter} cannot be found or initialized
     */
    <T> Converter<? super T> getConverter(Class<T> klass) throws ConverterInitializationException;
}
