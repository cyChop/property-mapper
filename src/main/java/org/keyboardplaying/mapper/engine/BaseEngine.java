package org.keyboardplaying.mapper.engine;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.keyboardplaying.mapper.annotation.BooleanValues;
import org.keyboardplaying.mapper.annotation.Temporal;
import org.keyboardplaying.mapper.converter.BooleanConverter;
import org.keyboardplaying.mapper.converter.Converter;
import org.keyboardplaying.mapper.converter.TemporalConverter;
import org.keyboardplaying.mapper.exception.ConverterInitializationException;
import org.keyboardplaying.mapper.exception.MappingException;

// XXX Study the opportunity of creating a MappingExceptionFactory
/**
 * An abstract base for engine. This class includes methods for fetching {@link Converter} instances
 * when mapping or unmapping.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public abstract class BaseEngine {

    /** The {@link ConverterProvider}. */
    private ConverterProvider converterProvider;

    /**
     * Sets the provider for fetching converters.
     *
     * @param converterProvider
     *            the converter provider
     */
    public void setConverterProvider(ConverterProvider converterProvider) {
        this.converterProvider = converterProvider;
    }

    /**
     * Returns the provider for fetching converters.
     * <p/>
     * If no provider was set, the {@link AutoDiscoverConverterProvider} will be used.
     *
     * @return the converter provider
     */
    private ConverterProvider getConverterProvider() {
        if (converterProvider == null) {
            converterProvider = AutoDiscoverConverterProvider.getInstance();
        }
        return converterProvider;
    }

    /**
     * Returns the appropriate {@link Converter} based on the supplied field's type.
     *
     * @param field
     *            the field to convert a value from or to
     * @return the {@link Converter} to use
     * @throws MappingException
     *             when no {@link Converter} can be found or annotation settings are missing (e.g.
     *             {@link Temporal} on temporal fields)
     * @throws ConverterInitializationException
     *             if the {@link Converter} cannot be found or initialized
     */
    @SuppressWarnings("unchecked")
    protected <T> Converter<? super T> getConverter(Field field) throws MappingException,
            ConverterInitializationException {
        Converter<? super T> converter = getConverterProvider().getConverter(
                (Class<T>) field.getType());

        if (converter == null) {

            throw new MappingException("No converter could be found for type " + field.getType()
                    + " (field " + field.getName() + " of " + field.getDeclaringClass().getName()
                    + ")");

        } else if (converter instanceof TemporalConverter) {

            if (field.isAnnotationPresent(Temporal.class)) {
                Temporal temporal = field.getAnnotation(Temporal.class);
                ((TemporalConverter<T>) converter).setFormat(temporal.value().getFormat());
            } else {
                throw new MappingException("Field " + field.getName() + " of "
                        + field.getDeclaringClass().getName()
                        + " must declare the @Temporal annotation.");
            }

        } else if (converter instanceof BooleanConverter
                && field.isAnnotationPresent(BooleanValues.class)) {

            BooleanValues annot = field.getAnnotation(BooleanValues.class);
            ((BooleanConverter) converter).setTrueFalse(annot.whenTrue(), annot.whenFalse());
        }

        return converter;
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
    protected Object get(Object bean, Field field) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, IntrospectionException {
        return new PropertyDescriptor(field.getName(), bean.getClass()).getReadMethod()
                .invoke(bean);
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
    protected void set(Object bean, Field field, Object value) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, IntrospectionException {
        new PropertyDescriptor(field.getName(), bean.getClass()).getWriteMethod().invoke(bean,
                value);
    }
}
