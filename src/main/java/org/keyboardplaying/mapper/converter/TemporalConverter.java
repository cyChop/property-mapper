package org.keyboardplaying.mapper.converter;

/**
 * Specific version of {@link Converter} for temporal objects.
 * <p/>
 * Fields converted using an implementation of this interface should declare the
 * {@link org.keyboardplaying.mapper.annotation.Temporal} annotation.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public interface TemporalConverter<T> extends Converter<T> {

    /**
     * Sets the format this converter should use.
     *
     * @param format
     *            the format this converter should use
     */
    void setFormat(String format);
}
