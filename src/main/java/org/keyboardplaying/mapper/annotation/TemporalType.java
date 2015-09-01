package org.keyboardplaying.mapper.annotation;

import org.keyboardplaying.mapper.Defaults;

/**
 * This enum lists the available time representations in the metadata mapping.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public enum TemporalType {
    /** For date values, not including the notion of time. */
    DATE(Defaults.FORMAT_DATE),
    /** For timestamp values, including both date and time. */
    DATETIME(Defaults.FORMAT_DATETIME);

    /** The format of the string representation of the date. */
    private String format;

    /**
     * Private constructor.
     *
     * @param format
     *            the format to use when parsing the {@link String} representation of a time or formatting the time to a
     *            {@link String}
     */
    private TemporalType(String format) {
        this.format = format;
    }

    /**
     * Returns the format to use when parsing the {@link String} representation of a time or formatting the time to a
     * {@link String}.
     *
     * @return the format to use when parsing between time objects and their {@link String} representation
     */
    public String getFormat() {
        return format;
    }
}
