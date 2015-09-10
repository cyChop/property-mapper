package org.keyboardplaying.mapper.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.keyboardplaying.mapper.parser.ElaborateParser;

/**
 * This annotation marks a field that should be mapped between map and bean. It should be used to map the bean's field
 * with the name of its counterpart in the map as a value.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Metadata {

    /**
     * Returns the key for the value in the map which should be used when setting this field.
     *
     * @return the key for this field's value in the metadata map
     */
    String value() default "";

    /**
     * Defines whether a field is mandatory.
     * <p/>
     * If the field is mandatory but missing when mapping (or missing in the map when unmapping), an exception will be
     * thrown.
     *
     * @return {@code true} if this field is mandatory, {@code false} otherwise
     */
    boolean mandatory() default false;

    /**
     * Defines the {@link ElaborateParser} to use when the parsing requires some logic.
     *
     * @return the {@link ElaborateParser}
     */
    Class<? extends ElaborateParser<?>>elaborate() default ElaborateParser.None.class;
}
