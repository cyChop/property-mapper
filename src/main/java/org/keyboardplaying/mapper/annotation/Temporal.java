package org.keyboardplaying.mapper.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark fields representing a time.
 * <p/>
 * More specifically, it defines which {@link TemporalType} should be used when mapping from and to the metadata map.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
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
}
