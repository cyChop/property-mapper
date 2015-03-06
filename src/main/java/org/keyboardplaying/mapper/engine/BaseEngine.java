package org.keyboardplaying.mapper.engine;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.keyboardplaying.mapper.Defaults;
import org.keyboardplaying.mapper.annotation.BooleanValues;
import org.keyboardplaying.mapper.annotation.Temporal;
import org.keyboardplaying.mapper.parser.BooleanParser;
import org.keyboardplaying.mapper.parser.Parser;
import org.keyboardplaying.mapper.parser.TemporalParser;
import org.keyboardplaying.mapper.exception.ParserInitializationException;
import org.keyboardplaying.mapper.exception.MappingException;

// XXX Study the opportunity of creating a MappingExceptionFactory
/**
 * An abstract base for engine. This class includes methods for fetching {@link Parser} instances
 * when mapping or unmapping.
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
     * Returns the appropriate {@link Parser} based on the supplied field's type.
     *
     * @param field
     *            the field to convert a value from or to
     * @return the {@link Parser} to use
     * @throws MappingException
     *             when no {@link Parser} can be found or annotation settings are missing (e.g.
     *             {@link Temporal} on temporal fields)
     * @throws ParserInitializationException
     *             if the {@link Parser} cannot be found or initialized
     */
    @SuppressWarnings("unchecked")
    protected <T> Parser<? super T> getParser(Field field) throws MappingException,
            ParserInitializationException {
        Parser<? super T> parser = getParserProvider().getParser((Class<T>) field.getType());

        if (parser == null) {

            throw new MappingException("No parser could be found for type " + field.getType()
                    + " (field " + field.getName() + " of " + field.getDeclaringClass().getName()
                    + ")");

        } else if (parser instanceof TemporalParser) {

            if (field.isAnnotationPresent(Temporal.class)) {
                Temporal temporal = field.getAnnotation(Temporal.class);
                ((TemporalParser<T>) parser).setFormat(temporal.value().getFormat());
            } else {
                throw new MappingException("Field " + field.getName() + " of "
                        + field.getDeclaringClass().getName()
                        + " must declare the @Temporal annotation.");
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
