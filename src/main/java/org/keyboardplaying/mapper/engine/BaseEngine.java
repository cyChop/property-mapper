package org.keyboardplaying.mapper.engine;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.keyboardplaying.mapper.Defaults;
import org.keyboardplaying.mapper.annotation.BooleanValues;
import org.keyboardplaying.mapper.annotation.Temporal;
import org.keyboardplaying.mapper.exception.FieldMappingException;
import org.keyboardplaying.mapper.exception.MappingException;
import org.keyboardplaying.mapper.exception.ParserInitializationException;
import org.keyboardplaying.mapper.parser.BooleanParser;
import org.keyboardplaying.mapper.parser.SimpleParser;
import org.keyboardplaying.mapper.parser.TemporalParser;

/**
 * An abstract base for engine. This class includes methods for fetching {@link SimpleParser} instances when mapping or
 * unmapping.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public abstract class BaseEngine {

    /** The {@link ParserProvider}. */
    private ParserProvider parserProvider;

    /**
     * Sets the provider for fetching parsers.
     *
     * @param parserProvider
     *            the parser provider
     */
    public void setParserProvider(ParserProvider parserProvider) {
        this.parserProvider = parserProvider;
    }

    /**
     * Returns the provider for fetching parsers.
     * <p/>
     * If no provider was set, the {@link AutoDiscoverParserProvider} will be used.
     *
     * @return the parser provider
     */
    private ParserProvider getParserProvider() {
        if (parserProvider == null) {
            parserProvider = AutoDiscoverParserProvider.getInstance();
        }
        return parserProvider;
    }

    /**
     * Returns the appropriate {@link SimpleParser} based on the supplied field's type.
     *
     * @param field
     *            the field to convert a value from or to
     * @return the {@link SimpleParser} to use
     * @throws MappingException
     *             if no {@link SimpleParser} can be found or annotation settings are missing (e.g. {@link Temporal} on
     *             temporal fields)
     * @throws ParserInitializationException
     *             if the {@link SimpleParser} cannot be found or initialized
     */
    @SuppressWarnings("unchecked")
    protected <T> SimpleParser<T> getParser(Field field) throws MappingException, ParserInitializationException {
        SimpleParser<T> parser = getParserProvider().getParser((Class<T>) field.getType());

        if (parser == null) {

            throw new MappingException("No parser could be found for type " + field.getType() + " (field "
                    + field.getName() + " of " + field.getDeclaringClass().getName() + ")");

        } else if (parser instanceof TemporalParser) {

            if (field.isAnnotationPresent(Temporal.class)) {
                Temporal temporal = field.getAnnotation(Temporal.class);
                ((TemporalParser<T>) parser).setFormat(temporal.value().getFormat());
            } else {
                throw new FieldMappingException(field, "This field must declare the @Temporal annotation.");
            }

        } else if (parser instanceof BooleanParser) {
            if (field.isAnnotationPresent(BooleanValues.class)) {
                BooleanValues annot = field.getAnnotation(BooleanValues.class);
                ((BooleanParser) parser).setTrueFalse(annot.whenTrue(), annot.whenFalse());
            } else {
                ((BooleanParser) parser).setTrueFalse(Defaults.TRUE, Defaults.FALSE);
            }
        }

        return parser;
    }

    /**
     * Returns a {@link PropertyDescriptor} for the supplied field.
     *
     * @param bean
     *            the bean to get a descriptor for
     * @param field
     *            the field to get a descriptor for
     * @return the {@link PropertyDescriptor}
     * @throws IntrospectionException
     *             if an exception occurs during introspection.
     */
    protected <T> PropertyDescriptor getPropertyDescriptor(T bean, Field field) throws IntrospectionException {
        return new PropertyDescriptor(field.getName(), bean.getClass());
    }

    /**
     * Gets the value of a field in the supplied bean.
     *
     * @param bean
     *            the bean
     * @param field
     *            the field
     * @return the value
     */
    protected <T> T get(Object bean, Field field)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
        return get(bean, getPropertyDescriptor(bean, field));
    }

    /**
     * Gets the value of a field in the supplied bean.
     *
     * @param bean
     *            the bean
     * @param descriptor
     *            the field's descriptor
     * @return the value
     */
    @SuppressWarnings("unchecked")
    protected <T> T get(Object bean, PropertyDescriptor descriptor)
            throws IllegalAccessException, InvocationTargetException {
        Method readMethod = descriptor.getReadMethod();
        if (readMethod == null) {
            throw new IllegalAccessException("Field " + descriptor.getName() + " does not possess a read method.");
        }
        return (T) readMethod.invoke(bean);
    }

    /**
     * Sets the value of a field in the supplied bean.
     *
     * @param bean
     *            the bean
     * @param field
     *            the field
     * @param value
     *            the value
     */
    protected void set(Object bean, Field field, Object value)
            throws IllegalAccessException, InvocationTargetException, IntrospectionException {
        set(bean, getPropertyDescriptor(bean, field), value);
    }

    /**
     * Sets the value of a field in the supplied bean.
     *
     * @param bean
     *            the bean
     * @param descriptor
     *            the field's descriptor
     * @param value
     *            the value
     */
    protected void set(Object bean, PropertyDescriptor descriptor, Object value)
            throws IllegalAccessException, InvocationTargetException {
        Method writeMethod = descriptor.getWriteMethod();
        if (writeMethod == null) {
            throw new IllegalAccessException("Field " + descriptor.getName() + " does not possess a write method.");
        }
        writeMethod.invoke(bean, value);
    }
}
