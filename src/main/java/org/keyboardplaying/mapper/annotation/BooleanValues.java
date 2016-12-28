package org.keyboardplaying.mapper.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.keyboardplaying.mapper.Defaults;

/**
 * Defines the {@link String} representations for {@code true} and {@code false} when dealing with boolean metadata.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BooleanValues {

    /**
     * The {@link String} representations of a {@code true} boolean.
     * <p/>
     * When unmapping, everyone of these values will be mapped to {@code true}. When mapping, the first value will be
     * the reference.
     *
     * @return the {@link String} representation of a {@code true} boolean.
     */
    String[] whenTrue() default {Defaults.TRUE};

    /**
     * The {@link String} representations of a {@code false} boolean.
     * <p/>
     * When unmapping, everyone of these values will be mapped to {@code false}. When mapping, the first value will be
     * the reference.
     *
     * @return the {@link String} representation of a {@code false} boolean.
     */
    String[] whenFalse() default {Defaults.FALSE};
}
