package org.keyboardplaying.mapper.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.keyboardplaying.mapper.Defaults;

/**
 * This annotation is used to mark fields representing a time.
 * <p/>
 * More specifically, it defines which {@link TemporalType} should be used when mapping from and to the metadata map.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Temporal {

    /**
     * Returns the {@link TemporalType} to use when mapping from and to the metadata map.
     *
     * @return the {@link TemporalType} to use when mapping from and to the metadata map
     */
    TemporalType value();

    /**
     * This enum lists the available time representations in the metadata mapping.
     *
     * @author Cyrille Chopelet (https://keyboardplaying.org)
     */
    enum TemporalType {
        /**
         * For date values, not including the notion of time.
         */
        DATE(Defaults.FORMAT_DATE),
        /**
         * For timestamp values, including both date and time.
         */
        DATETIME(Defaults.FORMAT_DATETIME);

        /**
         * The format of the string representation of the date.
         */
        private String format;

        /**
         * Private constructor.
         *
         * @param format the format to use when parsing the {@link String} representation of a time or formatting the time
         *               to a {@link String}
         */
        TemporalType(String format) {
            this.format = format;
        }

        /**
         * Returns the format to use when parsing the {@link String} representation of a time or formatting the time to
         * a {@link String}.
         *
         * @return the format to use when parsing between time objects and their {@link String} representation
         */
        public String getFormat() {
            return format;
        }
    }
}
