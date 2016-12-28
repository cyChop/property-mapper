package org.keyboardplaying.mapper.parser;

/**
 * Specific version of {@link SimpleParser} for temporal objects.
 * <p/>
 * Fields converted using an implementation of this interface should declare the
 * {@link org.keyboardplaying.mapper.annotation.Temporal} annotation.
 *
 * @param <T> the type of time objects this {@link SimpleParser} converts from and to
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public interface TemporalParser<T> extends SimpleParser<T> {

    /**
     * Sets the format this parser should use.
     *
     * @param format the format this parser should use
     */
    void setFormat(String format);
}
