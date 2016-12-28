package org.keyboardplaying.mapper.engine;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;

import org.keyboardplaying.mapper.Defaults;
import org.keyboardplaying.mapper.annotation.Metadata;
import org.keyboardplaying.mapper.annotation.Nested;
import org.keyboardplaying.mapper.exception.MapperException;
import org.keyboardplaying.mapper.exception.MappingException;
import org.keyboardplaying.mapper.exception.ParserInitializationException;
import org.keyboardplaying.mapper.exception.ParsingException;
import org.keyboardplaying.mapper.parser.ElaborateParser;

/**
 * The mapping engine for mapping a flat map to a POJO (unmapping).
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class UnmappingEngine extends BaseEngine {

    private static final DefaultValueProvider DEFAULT_VALUES = new DefaultValueProvider();

    /**
     * Instantiates a new bean of specified type and unmaps metadata to it, based on the annotations in the bean.
     *
     * @param metadata the flat metadata
     * @param beanType the destination bean's type
     * @return the destination bean
     * @throws MapperException if the parser could not be initialized for a field or the mapping fails
     */
    public <T> T unmapToClass(Map<String, String> metadata, Class<T> beanType) throws MapperException {
        try {
            return unmapToBean(metadata, beanType.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new MappingException("Could not instanciate a new bean for type " + beanType.getSimpleName()
                    + ". Did you provide a public no-argument constructor?", e);
        }
    }

    /**
     * Unmaps metadata to a destination bean, based on the annotations in the bean.
     * <p/>
     * Non-annotated fields are not overwritten.
     *
     * @param metadata the flat metadata
     * @param bean     the destination bean
     * @return the destination bean
     * @throws MapperException if the parser could not be initialized for a field or the mapping fails
     */
    public <T> T unmapToBean(Map<String, String> metadata, T bean) throws MapperException {
        /* Control the validity of arguments. */
        Objects.requireNonNull(bean, "The supplied bean was null.");
        Objects.requireNonNull(metadata, "The supplied metadata was null.");

        /* Now perform the unmapping. */
        performUnmapping(metadata, bean, bean.getClass());

        return bean;
    }

    private <T> void performUnmapping(Map<String, String> metadata, T bean, Class<?> klass)
            throws MapperException {
        final Field[] fields = klass.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Nested.class)) {
                performNestedUnmapping(metadata, bean, field);
            } else if (field.isAnnotationPresent(Metadata.class)) {
                performFieldUnmapping(metadata, bean, field);
            }
        }

        /* Take care of inherited fields. */
        Class<?> superklass = klass.getSuperclass();
        if (!superklass.equals(Object.class)) {
            performUnmapping(metadata, bean, superklass);
        }
    }

    /**
     * Performs the unmapping to a field which is an inner bean (marked with the {@link Nested} annotation).
     *
     * @param metadata the flat metadata
     * @param bean     the destination bean
     * @param field    the field to set
     * @throws MapperException if the parser could not be initialized for a field or the mapping fails
     */
    private <T> void performNestedUnmapping(Map<String, String> metadata, T bean, Field field) throws MapperException {
        Nested annotation = field.getAnnotation(Nested.class);
        try {

            PropertyDescriptor descriptor = getPropertyDescriptor(bean, field);

            Object innerBean = get(bean, descriptor);
            if (innerBean == null) {
                innerBean = intantiateBeanAndUnmap(metadata, field, annotation.className());
                set(bean, descriptor, innerBean);
            } else {
                // unmap to bean
                unmapToBean(metadata, innerBean);
            }

        } catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
            throw new MappingException("Error while unmapping nested bean " + field.getName() + " of "
                    + field.getDeclaringClass().getName(), e);
        } catch (MapperException e) {
            if (annotation.mandatory()) {
                throw e;
            }
            // XXX some log here would be great otherwise
        }
    }

    private Object intantiateBeanAndUnmap(Map<String, String> metadata, Field field, String className)
            throws MapperException {
        try {
            // unmap to class
            // compilation fails when variable is inline (?!)
            Class<?> beanType = className == null || className.length() == 0 ? field.getType() : Class.forName(className);
            return unmapToClass(metadata, beanType);
        } catch (ClassNotFoundException e) {
            throw new MappingException("Could not find class " + className + " when instantiating bean for inner field "
                    + field.getName() + " of class " + field.getDeclaringClass().getName(), e);
        }
    }

    /**
     * Performs the unmapping for a convertible-typed field.
     *
     * @param metadata the flat metadata
     * @param bean     the destination bean
     * @param field    the field to set
     * @throws ParserInitializationException if the parser for the field could not be initialized
     * @throws MappingException              if the mapping fails
     */
    private <T> void performFieldUnmapping(Map<String, String> metadata, T bean, Field field)
            throws ParserInitializationException, MappingException {
        Metadata settings = field.getAnnotation(Metadata.class);

        Class<? extends ElaborateParser<?>> elaborate = settings.elaborate();
        String metadataName = settings.value();

        if (!ElaborateParser.None.class.equals(elaborate)) {

            // a custom setter was defined, overrides the default parser
            setElaborateField(bean, field, elaborate, metadata);

        } else if (Defaults.EMPTY.equals(metadataName)) {

            throw new MappingException("No key nor elaborate parser was provided for field " + field.getName()
                    + " of bean " + bean.getClass().getSimpleName());

        } else if (metadata.containsKey(metadataName)) {

            /* Set the value using the value provided with the metadata. */
            setField(bean, field, metadata.get(metadataName));

        } else if (!settings.defaultValue().equals(Defaults.EMPTY)) {

            /* Set the value using the provided default value. */
            setField(bean, field, settings.defaultValue());

        } else if (settings.blankDefaultValue()) {

            /* Set the field using a blank value. */
            setField(bean, field, Defaults.EMPTY);

        } else if (settings.mandatory()) {

            /* Data is absent though mandatory, raise an exception. */
            throw new MappingException("Mandatory data " + metadataName + " is missing from metadata map ("
                    + metadata.keySet().toString() + ").");
        }
        /* Otherwise, leave field as is. */
    }

    /**
     * Sets the field, using the default parser for the type of the field and the field's setter.
     * <p/>
     * This requires the bean to respect the bean notation.
     *
     * @param bean  the destination bean
     * @param field the field to set
     * @param value the non-converted value for the field
     * @throws ParserInitializationException if the parser for the field could not be initialized
     * @throws MappingException              if the mapping fails
     */
    private <T> void setField(T bean, Field field, String value)
            throws ParserInitializationException, MappingException {
        try {
            set(bean, field, value == null ? DEFAULT_VALUES.getDefaultValue(field.getType())
                    : this.<T>getParser(field).convertFromString(value));
        } catch (IllegalAccessException | InvocationTargetException | IntrospectionException | ParsingException e) {
            throw new MappingException(
                    "Field " + field.getName() + " of " + field.getDeclaringClass().getName() + " could not be set.",
                    e);
        }
    }

    /**
     * Sets the field, using the supplied setter.
     * <p/>
     * The signature for the setter should be {@code setSomething(java.lang.String, java.util.Map<String, String>)}. It
     * will receive the value for the required metadata as the first argument, and the whole flat metadata as the
     * second.
     *
     * @param bean     the destination bean
     * @param field    the field to set
     * @param parser   the {@link ElaborateParser} to use
     * @param metadata the flat metadata
     * @throws MappingException if the mapping fails
     */
    private <T> void setElaborateField(T bean, Field field, Class<? extends ElaborateParser<?>> parser,
                                       Map<String, String> metadata) throws MappingException {
        try {
            Object value = parser.newInstance().fromMap(metadata);
            set(bean, field, value == null ? DEFAULT_VALUES.getDefaultValue(field.getType()) : value);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | IntrospectionException | ParsingException e) {
            throw new MappingException(
                    "Serialization through parser " + parser.getSimpleName() + " could not be performed for field "
                            + field.getName() + " of bean " + bean.getClass().getSimpleName(),
                    e);
        }
    }
}
