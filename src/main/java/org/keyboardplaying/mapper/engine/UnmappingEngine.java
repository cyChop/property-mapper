package org.keyboardplaying.mapper.engine;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.keyboardplaying.mapper.annotation.DefaultValue;
import org.keyboardplaying.mapper.annotation.Metadata;
import org.keyboardplaying.mapper.annotation.Nested;
import org.keyboardplaying.mapper.exception.MappingException;
import org.keyboardplaying.mapper.exception.ParserInitializationException;
import org.keyboardplaying.mapper.exception.ParsingException;
import org.keyboardplaying.mapper.parser.ElaborateParser;

/**
 * The mapping engine for mapping a flat map to a POJO (unmapping).
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class UnmappingEngine extends BaseEngine {

    /** A map containing the default values of objects based on their types. */
    private static final Map<Class<?>, Object> DEFAULT_VALUES;

    static {
        /*
         * Initialize default values for primitive types.
         *
         * Skip Objects: their default value is null anyway.
         */
        // do not declare a serial: it would provide a default value for a String.
        @SuppressWarnings("serial")
        Map<Class<?>, Object> defaultValues = new HashMap<Class<?>, Object>() {
            /* Default primitive values (called through reflection). */
            @SuppressWarnings("unused")
            private boolean b;
            @SuppressWarnings("unused")
            private byte by;
            @SuppressWarnings("unused")
            private char c;
            @SuppressWarnings("unused")
            private double d;
            @SuppressWarnings("unused")
            private float f;
            @SuppressWarnings("unused")
            private int i;
            @SuppressWarnings("unused")
            private long l;
            @SuppressWarnings("unused")
            private short s;

            {
                try {
                    for (final Field field : getClass().getDeclaredFields()) {
                        put(field.getType(), field.get(this));
                    }
                } catch (IllegalAccessException e) {
                    // this will never occur, or the application will not work properly
                    throw new IllegalStateException(e);
                }
            }
        };

        DEFAULT_VALUES = Collections.unmodifiableMap(defaultValues);
    }

    /**
     * Instantiates a new bean of specified type and unmaps metadata to it, based on the annotations in the bean.
     *
     * @param metadata
     *            the flat metadata
     * @param beanType
     *            the destination bean's type
     * @return the destination bean
     * @throws ParserInitializationException
     *             when the parser could not be initialized for a field
     * @throws MappingException
     *             when the mapping fails
     */
    public <T> T unmap(Map<String, String> metadata, Class<T> beanType)
            throws ParserInitializationException, MappingException {
        try {
            return unmap(metadata, beanType.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new MappingException("Could not instanciate a new bean for type " + beanType.getSimpleName() + ".",
                    e);
        }
    }

    /**
     * Unmaps metadata to a destination bean, based on the annotations in the bean.
     * <p/>
     * Non-annotated fields are not overwritten.
     *
     * @param metadata
     *            the flat metadata
     * @param bean
     *            the destination bean
     * @return the destination bean
     * @throws ParserInitializationException
     *             when the parser could not be initialized for a field
     * @throws MappingException
     *             when the mapping fails
     */
    public <T> T unmap(Map<String, String> metadata, T bean) throws ParserInitializationException, MappingException {
        /* Control the validity of arguments. */
        if (bean == null) {
            throw new MappingException("The supplied bean was null.");
        } else if (metadata == null) {
            throw new MappingException("The supplied metadata was null.");
        }

        /* Now perform the unmapping. */
        final Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Nested.class)) {
                performInnerUnmapping(metadata, bean, field);
            } else if (field.isAnnotationPresent(Metadata.class)) {
                performFieldUnmapping(metadata, bean, field);
            }
        }

        return bean;
    }

    /**
     * Performs the unmapping to a field which is an inner bean (marked with the {@link Nested} annotation).
     *
     * @param metadata
     *            the flat metadata
     * @param bean
     *            the destination bean
     * @param field
     *            the field to set
     * @throws ParserInitializationException
     *             when the parser could not be initialized for a field
     * @throws MappingException
     *             when the mapping fails
     */
    private <T> void performInnerUnmapping(Map<String, String> metadata, T bean, Field field)
            throws ParserInitializationException, MappingException {
        try {

            PropertyDescriptor descriptor = getPropertyDescriptor(bean, field);

            Object innerBean = get(bean, descriptor);
            if (innerBean == null) {
                String className = field.getAnnotation(Nested.class).className();
                try {
                    innerBean = unmap(metadata,
                            className == null || className.length() == 0 ? field.getType() : Class.forName(className));
                } catch (ClassNotFoundException e) {
                    throw new MappingException(
                            "Could not find class " + className + " when instantiating bean for inner field "
                                    + field.getName() + " of class " + field.getDeclaringClass().getName(),
                            e);
                }
            } else {
                innerBean = unmap(metadata, innerBean);
            }

            set(bean, descriptor, innerBean);

        } catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
            throw new MappingException("Error while unmapping nested bean " + field.getName() + " of "
                    + field.getDeclaringClass().getName(), e);
        }
    }

    /**
     * Performs the unmapping for a convertible-typed field.
     *
     * @param metadata
     *            the flat metadata
     * @param bean
     *            the destination bean
     * @param field
     *            the field to set
     * @throws ParserInitializationException
     *             when the parser for the field could not be initialized
     * @throws MappingException
     *             when the mapping fails
     */
    private <T> void performFieldUnmapping(Map<String, String> metadata, T bean, Field field)
            throws ParserInitializationException, MappingException {
        Metadata settings = field.getAnnotation(Metadata.class);

        Class<? extends ElaborateParser<?>> elaborate = settings.elaborate();
        String metadataName = settings.value();

        if (!ElaborateParser.None.class.equals(elaborate)) {

            // a custom setter was defined, overrides the default parser
            setElaborateField(bean, field, elaborate, metadata);

        } else if ("".equals(metadataName)) {

            throw new MappingException("No key nor elaborate parser was provided for field " + field.getName()
                    + " of bean " + bean.getClass().getSimpleName());

        } else if (metadata.containsKey(metadataName)) {

            /* Set the value using the value provided with the metadata. */
            setField(bean, field, metadata.get(metadataName));

        } else if (settings.mandatory()) {

            /* Data is absent though mandatory, raise an exception. */
            throw new MappingException("Mandatory data " + metadataName + " is missing from metadata map ("
                    + metadata.keySet().toString() + ").");

        } else if (field.isAnnotationPresent(DefaultValue.class)) {

            /* Set the value using the provided default value. */
            setField(bean, field, field.getAnnotation(DefaultValue.class).value());
        }
        /* Otherwise, leave field as is. */
    }

    /**
     * Sets the field, using the default parser for the type of the field and the field's setter.
     * <p/>
     * This requires the bean to respect the bean notation.
     *
     * @param bean
     *            the destination bean
     * @param field
     *            the field to set
     * @param value
     *            the non-converted value for the field
     * @throws ParserInitializationException
     *             when the parser for the field could not be initialized
     * @throws MappingException
     *             when the mapping fails
     */
    private <T> void setField(T bean, Field field, String value)
            throws ParserInitializationException, MappingException {
        try {
            set(bean, field, value == null ? DEFAULT_VALUES.get(field.getType())
                    : this.<T> getParser(field).convertFromString(value));
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
     * @param bean
     *            the destination bean
     * @param field
     *            the field to set
     * @param parser
     *            the {@link ElaborateParser} to use
     * @param metadata
     *            the flat metadata
     * @throws MappingException
     *             when the mapping fails
     */
    private <T> void setElaborateField(T bean, Field field, Class<? extends ElaborateParser<?>> parser,
            Map<String, String> metadata) throws MappingException {
        try {
            Object value = parser.newInstance().fromMap(metadata);
            set(bean, field, value == null ? DEFAULT_VALUES.get(field.getType()) : value);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | IntrospectionException | ParsingException e) {
            throw new MappingException(
                    "Serialization through parser " + parser.getSimpleName() + " could not be performed for field "
                            + field.getName() + " of bean " + bean.getClass().getSimpleName(),
                    e);
        }
    }
}
