package org.keyboardplaying.mapper.engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.keyboardplaying.mapper.exception.ParserInitializationException;
import org.keyboardplaying.mapper.parser.SimpleParser;

/**
 * Provides the correct implementation of {@link SimpleParser} to use based on the type of the field to convert.
 * <p/>
 * <h1>Adding custom parsers</h1> It is possible to add custom parsers. To do so, you need to create a
 * {@code META-INF/services/org.keyboardplaying.mapper.parser} directory in your project.
 * <p/>
 * In this directory, you will need to add one descriptor file per parser. This file will be named after the fully
 * qualified type of field your parser handles, and contain only one line which is the fully qualified class of your
 * parser. For example, in the case of the {@link org.keyboardplaying.mapper.parser.StringParser}, the file is:
 *
 * <pre>
 * META - INF / services / org.keyboardplaying.mapper.parser / java.lang.String
 * </pre>
 *
 * and its content is:
 *
 * <pre>
 * parser = org.keyboardplaying.mapper.parser.StringParser
 * </pre>
 * <p/>
 * This provider instantiates the parsers only when required and then return them as singletons.
 * <p/>
 * This class implements the singleton design pattern.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class AutoDiscoverParserProvider implements ParserProvider {

    private static final String CONVERTER_DEFINITION_PATH = "META-INF/services/org.keyboardplaying.mapper.parser/";
    private static final String CONVERTER_PROPERTY = "parser";

    private static final AutoDiscoverParserProvider instance = new AutoDiscoverParserProvider();

    /** A list of parser types to use based on the field type. */
    private final Map<Class<?>, Class<? extends SimpleParser<?>>> parserDefinitions = new HashMap<>();
    /** A list of all previously loaded parsers based on their type. */
    private final Map<Class<? extends SimpleParser<?>>, SimpleParser<?>> parsers = new HashMap<>();

    /* Private constructor. */
    private AutoDiscoverParserProvider() {
    }

    /**
     * Returns the single instance of this class.
     *
     * @return the single instance of this class
     */
    public static AutoDiscoverParserProvider getInstance() {
        return instance;
    }

    /**
     * Fetches the appropriate {@link SimpleParser} for the supplied class.
     *
     * @param klass
     *            the class to convert from or to
     * @return the {@link SimpleParser} to use for parsing
     * @throws ParserInitializationException
     *             if the {@link SimpleParser} cannot be found or initialized
     */
    @Override
    public <T> SimpleParser<T> getParser(Class<T> klass) throws ParserInitializationException {
        Objects.requireNonNull(klass, "A class must be provided when requiring a parser.");

        @SuppressWarnings("unchecked")
        Class<? extends SimpleParser<T>> parserClass = (Class<? extends SimpleParser<T>>) parserDefinitions.get(klass);
        if (parserClass == null) {
            parserClass = getParserClass(klass);
            parserDefinitions.put(klass, parserClass);
        }

        @SuppressWarnings("unchecked")
        SimpleParser<T> parser = (SimpleParser<T>) parsers.get(parserClass);
        if (parser == null) {
            try {
                parser = parserClass.newInstance();
                parsers.put(parserClass, parser);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new ParserInitializationException(parserClass.getName()
                        + " could not be instanciated. Does it define a public no-arg constructor?", e);
            }
        }
        return parser;
    }

    /**
     * Returns the class of the parser to use for a specific class.
     *
     * @param klass
     *            the class to look a parser for
     * @return the class for the parser to use
     * @throws ParserInitializationException
     *             if the {@link SimpleParser} cannot be found or initialized
     */
    private <T> Class<? extends SimpleParser<T>> getParserClass(Class<T> klass) throws ParserInitializationException {
        String parserClassName = getParserClassName(klass);

        try {
            return getActualParserClass(parserClassName);
        } catch (ParserInitializationException e) {
            throw new ParserInitializationException(
                    "Failed to initialize parser " + parserClassName + " for type " + klass.getName() + ".", e);
        }
    }

    /**
     * Reads the parser's class name from the mapper's configuration.
     *
     * @param klass
     *            the class of the field to parse
     * @return the parser's class name
     * @throws ParserInitializationException
     *             if the mapper file could not be read
     */
    private <T> String getParserClassName(Class<T> klass) throws ParserInitializationException {
        String uri = CONVERTER_DEFINITION_PATH + klass.getName();

        String parserClassName;
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(uri)) {
            if (in == null) {
                throw new ParserInitializationException("No parser descriptor found for type " + klass.getName() + ".");
            }

            Properties properties = new Properties();
            properties.load(in);

            parserClassName = properties.getProperty(CONVERTER_PROPERTY);
            if (parserClassName == null) {
                throw new ParserInitializationException(
                        "SimpleParser descriptor for class " + klass.getName() + " is incorrect (empty).");
            }
        } catch (IOException e) {
            throw new ParserInitializationException("Error occurred when trying to read parser descriptor for type "
                    + klass.getName() + ", mapper file could not be read.", e);
        }
        return parserClassName;
    }

    /**
     * Loads the parser class from the supplied name and ensures it is a parser.
     *
     * @param parserClassName
     *            the class name for the parser
     * @return the parser class
     * @throws ParserInitializationException
     *             if the parser class could not be found or does not extend {@link SimpleParser}
     */
    @SuppressWarnings("unchecked")
    private <T> Class<? extends SimpleParser<T>> getActualParserClass(String parserClassName)
            throws ParserInitializationException {
        try {
            Class<?> parserClass = Class.forName(parserClassName);
            if (!SimpleParser.class.isAssignableFrom(parserClass)) {
                throw new ParserInitializationException(
                        "Class " + parserClassName + " does not extend " + SimpleParser.class.getName());
            }
            return (Class<? extends SimpleParser<T>>) parserClass;
        } catch (ClassNotFoundException e) {
            throw new ParserInitializationException("Class " + parserClassName + " could not be found.", e);
        }
    }
}
