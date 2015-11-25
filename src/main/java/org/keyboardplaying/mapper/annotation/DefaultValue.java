package org.keyboardplaying.mapper.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allows for settings a default value when the source value is {@code null} or not defined.
 * <p/>
 * In the mapping sense, the default value will be set to the defined {@link Metadata} field as is. In case of an
 * unmapping, the value set to the bean's field will be the result from converting the default value.
 * <p/>
 * This has been made a separate annotation to allow for the empty {@link String} default value.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
// FIXME differentiate the default value and default datum
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DefaultValue {

    /**
     * Returns the default value of the field.
     *
     * @return the default value for the field
     */
    String value();
}
