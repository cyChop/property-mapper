package org.keyboardplaying.mapper.parser;

/**
 * Specific version of {@link SimpleParser} for temporal objects.
 * <p/>
 * Fields converted using an implementation of this interface should declare the
 * {@link org.keyboardplaying.mapper.annotation.Temporal} annotation.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 * @param <T>
 *            the type of time objects this {@link SimpleParser} converts from and to
 */
public interface TemporalParser<T> extends SimpleParser<T> {

    /**
     * Sets the format this parser should use.
     *
     * @param format
     *            the format this parser should use
     */
    void setFormat(String format);
}
