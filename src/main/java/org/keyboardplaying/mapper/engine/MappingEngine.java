package org.keyboardplaying.mapper.engine;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.keyboardplaying.mapper.annotation.DefaultValue;
import org.keyboardplaying.mapper.annotation.Metadata;
import org.keyboardplaying.mapper.annotation.Nested;
import org.keyboardplaying.mapper.exception.MappingException;
import org.keyboardplaying.mapper.exception.ParserInitializationException;
import org.keyboardplaying.mapper.exception.ParsingException;

// TODO JAVADOC
/**
 * The mapping engine for mapping an annotated POJO to a flat {@link Map} (mapping).
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class MappingEngine extends BaseEngine {

    /**
     * Converts the annotated bean to {@link Map}.
     *
     * @param bean
     *            the annotated bean
     * @return the {@link Map} containing the mapped properties
     * @throws ParserInitializationException
     *             if a Parser could not be initialized
     * @throws MappingException
     *             if the mapping fails
     * @throws NullPointerException
     *             if the supplied bean is {@code null}
     */
    public <T> Map<String, String> map(T bean) throws ParserInitializationException, MappingException {
        return map(bean, new HashMap<String, String>());
    }

    /**
     * Converts the annotated bean to {@link Map}.
     *
     * @param bean
     *            the annotated bean
     * @param map
     *            the destination map
     * @return the {@link Map} containing the mapped properties
     * @throws ParserInitializationException
     *             if a Parser could not be initialized
     * @throws MappingException
     *             if the mapping fails
     * @throws NullPointerException
     *             if either parameter is {@code null}
     */
    public <T> Map<String, String> map(T bean, Map<String, String> map)
            throws ParserInitializationException, MappingException {
        Objects.requireNonNull(map, "The destination map must not be null.");

        /* Control the validity of arguments. */
        if (bean == null) {
            throw new NullPointerException("The supplied bean was null.");
        }

        /* Go and parse. */
        final Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Nested.class)) {
                performInnerMapping(bean, field, map);
            } else if (field.isAnnotationPresent(Metadata.class)) {
                performFieldMapping(bean, field, map);
            }
        }

        return map;
    }

    private <T> void performInnerMapping(T bean, Field field, Map<String, String> result)
            throws ParserInitializationException, MappingException {
        try {

            Object value = get(bean, field);
            if (value != null) {
                map(value, result);
            }

        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException
                | IntrospectionException e) {
            throw new MappingException(
                    "Error while mapping nested " + field.getName() + " of " + field.getDeclaringClass().getName(), e);
        }
    }

    private <T> void performFieldMapping(T bean, Field field, Map<String, String> map)
            throws ParserInitializationException, MappingException {
        Metadata settings = field.getAnnotation(Metadata.class);

        /* Fetch the value from the bean. */
        String value = getFieldAsString(bean, field, settings);
        if (value == null) {

            /* Field is null. */

            if (field.isAnnotationPresent(DefaultValue.class)) {

                /* Use the default value instead. */
                value = field.getAnnotation(DefaultValue.class).value();

            } else if (settings.mandatory()) {

                /* Data is absent though mandatory, raise an exception. */
                throw new MappingException("Mandatory field " + field.getName() + " of "
                        + field.getDeclaringClass().getName() + " is null and does not define a default value.");
            }
        }
        map.put(settings.value(), value);
    }

    private <T> String getFieldAsString(T bean, Field field, Metadata settings)
            throws ParserInitializationException, MappingException {
        String customGetter = settings.customGetter();

        String result;
        if (customGetter == null || customGetter.length() == 0) {
            result = getFieldAsString(bean, field);
        } else {
            // a custom getter was defined, overrides the default parser
            result = getFieldUsingCustomGetter(bean, customGetter);
        }
        return result;
    }

    private <T> String getFieldAsString(T bean, Field field) throws ParserInitializationException, MappingException {
        try {

            Object value = get(bean, field);
            return value == null ? null : getParser(field).convertToString(value);

        } catch (IllegalAccessException | ParsingException | InvocationTargetException | IntrospectionException e) {
            throw new MappingException(
                    "Field " + field.getName() + " of " + field.getDeclaringClass().getName() + " could not be got.",
                    e);
        }
    }

    private <T> String getFieldUsingCustomGetter(T bean, String customGetter) throws MappingException {
        try {

            Method method = bean.getClass().getMethod(customGetter);
            return (String) method.invoke(bean);

        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | SecurityException
                | NoSuchMethodException | ClassCastException e) {
            throw new MappingException(
                    "Custom getter " + customGetter + " of " + bean.getClass().getName() + " did not return a String",
                    e);
        }
    }
}
