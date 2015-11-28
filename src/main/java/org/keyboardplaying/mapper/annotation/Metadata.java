package org.keyboardplaying.mapper.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.keyboardplaying.mapper.Defaults;
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
    String value() default Defaults.EMPTY;

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
     * Defines the default value to use when unmapping metadata if this metadata does not have a value for the key for
     * this target.
     * <p/>
     * The same parser will be used as if the data came from the metadata map.
     * <p/>
     * <strong>This attribute does not work if you want an empty string as default.</strong> In this case, please see
     * {@link #blankDefaultValue()}.
     *
     * @return the default value when unmapping the annotated target
     */
    String defaultValue() default Defaults.EMPTY;

    /**
     * If {@code true} and not {@link #defaultValue()} is provided, an empty string will be used instead.
     *
     * @return {@code true} if an empty string should be used as {@link #defaultValue()}
     */
    boolean blankDefaultValue() default false;

    /**
     * Defines the default value to use when mapping metadata if this target is {@code null}.
     * <p/>
     * The value will be inserted into the metadata map as is.
     * <p/>
     * <strong>This attribute does not work if you want an empty string as default.</strong> In this case, please see
     * {@link #blankDefaultMetadata()}.
     *
     * @return the default value when unmapping the annotated target
     */
    String defaultMetadata() default Defaults.EMPTY;

    /**
     * If {@code true} and not {@link #defaultMetadata()} is provided, an empty string will be used instead.
     *
     * @return {@code true} if an empty string should be used as {@link #defaultMetadata()}
     */
    boolean blankDefaultMetadata() default false;

    /**
     * Defines the {@link ElaborateParser} to use when the parsing requires some logic.
     *
     * @return the {@link ElaborateParser}
     */
    Class<? extends ElaborateParser<?>> elaborate() default ElaborateParser.None.class;
}
