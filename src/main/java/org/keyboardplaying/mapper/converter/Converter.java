package org.keyboardplaying.mapper.converter;

import org.keyboardplaying.mapper.engine.ConverterProvider;
import org.keyboardplaying.mapper.exception.ConversionException;

/**
 * Implementations of this interface are to be used to switch from object to {@code String}.
 * <p/>
 * It should be implemented in such a way that, for any object,
 * {@code o.equals(convertFromString(convertToString(o)))} is {@code true}.
 * <p/>
 * <strong>Notice:</strong> This interface does not require for implementations to be null-safe. It
 * is up to the developer to ensure non-nullity when calling a {@link Converter}.
 * <p/>
 * <strong>Notice:</strong> Please note that all implementations of this interface should provide a
 * {@code public} no-arg constructor, so that the {@link ConverterProvider} is able to instantiate
 * any {@link Converter}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 * @param <T>
 *            the type of object being parsed from and to {@code String}
 */
public interface Converter<T> {

    /**
     * Parses a {@code String} to an object.
     * <p/>
     * Implementations do not need to be null-safe.
     *
     * @param value
     *            the {@code String} value accepted
     * @return the object
     * @throws ConversionException
     *             if the conversion cannot be performed
     */
    T convertFromString(String value) throws ConversionException;

    /**
     * Parses an object to its {@code String} representation.
     * <p/>
     * Implementations do not need to be null-safe.
     *
     * @param value
     *            the object to parse
     * @return the {@code String} representation
     * @throws ConversionException
     *             if the conversion cannot be performed
     */
    String convertToString(T value) throws ConversionException;
}
