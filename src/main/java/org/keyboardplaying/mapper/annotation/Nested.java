package org.keyboardplaying.mapper.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a field which is a bean whose fields should be included in the
 * mapping process.
 * <p/>
 * When the field type is not the type of the object to instantiate, the fully qualified name of the
 * class should be provided.
 * <p/>
 * Any nested bean should provide a {@code public} no-argument constructor.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Nested {

    /**
     * Returns the fully qualified class name for the bean to instantiate and store into this field.
     * <p/>
     * This attribute is optional but must be provided when the field type is abstract or
     *
     * @return the fully qualified
     */
    String className() default "";
}
