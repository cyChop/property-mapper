package org.keyboardplaying.mapper.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.keyboardplaying.mapper.Defaults;

/**
 * This annotation is used to mark a field which is a bean whose fields should be included in the mapping process.
 * <p/>
 * When the field type is not the type of the object to instantiate, the fully qualified name of the class should be
 * provided.
 * <p/>
 * Any nested bean should provide a {@code public} no-argument constructor.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
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
     * @return the fully qualified implementation to use
     */
    String className() default Defaults.EMPTY;

    /**
     * Defines whether a nested object is mandatory or not.
     * <p/>
     * In all cases, the mapper will try to map the nested bean. If a mandatory nested bean is missing, the mapper will
     * throw an error.
     * <p/>
     * The unmapper will also try to unmap the nested bean. If this method returns false {@code false}, the nested bean
     * will be {@code null} in case of an error. If it is {@code true}, the mapping errors will be propagated.
     *
     * @return {@code true} if nested bean is mandatory, {@code false} otherwise
     */
    boolean mandatory() default false;
}
