package org.keyboardplaying.mapper.engine;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.keyboardplaying.mapper.Defaults;
import org.keyboardplaying.mapper.annotation.Metadata;
import org.keyboardplaying.mapper.annotation.Nested;
import org.keyboardplaying.mapper.exception.FieldMappingException;
import org.keyboardplaying.mapper.exception.MapperException;
import org.keyboardplaying.mapper.exception.MappingException;
import org.keyboardplaying.mapper.exception.ParserInitializationException;
import org.keyboardplaying.mapper.exception.ParsingException;
import org.keyboardplaying.mapper.parser.ElaborateParser;

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
     * @throws MapperException
     *             if a SimpleParser could not be initialized or the mapping fails
     * @throws NullPointerException
     *             if the supplied bean is {@code null}
     */
    public <T> Map<String, String> map(T bean) throws MapperException {
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
     * @throws MapperException
     *             if a SimpleParser could not be initialized or the mapping fails
     * @throws NullPointerException
     *             if either parameter is {@code null}
     */
    public <T> Map<String, String> map(T bean, Map<String, String> map) throws MapperException {
        Objects.requireNonNull(map, "The destination map must not be null.");

        /* Control the validity of arguments. */
        Objects.requireNonNull(bean, "The supplied bean was null.");

        /* Go and parse. */
        performMapping(bean, map, bean.getClass());

        return map;
    }

    private <T> void performMapping(T bean, Map<String, String> map, Class<?> klass)
            throws MapperException, ParserInitializationException, MappingException {
        final Field[] fields = klass.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Nested.class)) {
                performInnerMapping(bean, field, map);
            } else if (field.isAnnotationPresent(Metadata.class)) {
                performFieldMapping(bean, field, map);
            }
        }

        /* Take care of inherited fields. */
        // TODO #9 also examine interfaces
        final Class<?> superklass = klass.getSuperclass();
        if (!superklass.equals(Object.class)) {
            performMapping(bean, map, superklass);
        }
    }

    private <T> void performInnerMapping(T bean, Field field, Map<String, String> result) throws MapperException {
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

    private <T, F> void performFieldMapping(T bean, Field field, Map<String, String> map)
            throws ParserInitializationException, MappingException {
        Metadata settings = field.getAnnotation(Metadata.class);

        try {
            F fieldValue = get(bean, field);

            if (fieldValue == null) {

                /* Field is null. */
                if (!settings.defaultMetadata().equals(Defaults.EMPTY)) {

                    /* Use the default value instead. */
                    setValue(map, settings, settings.defaultMetadata());

                } else if (settings.blankDefaultMetadata()) {

                    /* Use the default value instead. */
                    setValue(map, settings, Defaults.EMPTY);

                } else if (settings.mandatory()) {

                    /* Data is absent though mandatory, raise an exception. */
                    throw new MappingException("Mandatory field " + field.getName() + " of "
                            + field.getDeclaringClass().getName() + " is null and does not define a default value.");
                } else {
                    serializeField(bean, field, settings, map);
                }

            } else {
                serializeField(bean, field, settings, map);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | IntrospectionException e) {
            throw new FieldMappingException(field, "Field could not be serialized.", e);
        }
    }

    private <F, T> void serializeField(T bean, Field field, Metadata settings, Map<String, String> map)
            throws ParserInitializationException, MappingException {
        @SuppressWarnings("unchecked")
        Class<? extends ElaborateParser<F>> elaborate = (Class<? extends ElaborateParser<F>>) settings.elaborate();
        if (elaborate.equals(ElaborateParser.None.class)) {
            // simple parse and store
            setValue(map, settings, field == null ? null : getFieldAsString(bean, field));
        } else {
            // a custom getter was defined, overrides the default parser
            serializeField(bean, field, map, elaborate);
        }
    }

    private <T> String getFieldAsString(T bean, Field field) throws ParserInitializationException, MappingException {
        try {

            Object value = get(bean, field);
            return value == null ? null : getParser(field).convertToString(value);

        } catch (IllegalAccessException | ParsingException | InvocationTargetException | IntrospectionException e) {
            throw new FieldMappingException(field, "Field could not be read.", e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T, F> void serializeField(T bean, Field field, Map<String, String> map,
            Class<? extends ElaborateParser<F>> parser) throws MappingException {
        try {
            parser.newInstance().toMap((F) get(bean, field), map);
        } catch (ParsingException | InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | IntrospectionException e) {
            throw new FieldMappingException(field,
                    "Field could not be serialized using parser " + parser.getClass().getSimpleName() + ".", e);
        }
    }

    private void setValue(Map<String, String> map, Metadata settings, String value) {
        map.put(settings.value(), value);
    }
}
