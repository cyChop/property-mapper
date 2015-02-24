package org.keyboardplaying.mapper.converter;

import org.keyboardplaying.mapper.Defaults;
import org.keyboardplaying.mapper.exception.ConversionException;

/**
 * Implementation of {@link Converter} for {@code Boolean} to {@code String} conversion.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class BooleanConverter implements Converter<Boolean> {

    /** The {@link String} representation of {@code true}. */
    private String whenTrue = Defaults.BOOLEAN_YES;
    /** The {@link String} representation of {@code false}. */
    private String whenFalse = Defaults.BOOLEAN_NO;

    /**
     * Sets the values to use for {@code true} and {@code false} values.
     *
     * @param trueString
     *            the {@link String} representation of {@code true}
     * @param falseString
     *            the {@link String} representation of {@code false}
     */
    public void setTrueFalse(String trueString, String falseString) {
        this.whenTrue = trueString;
        this.whenFalse = falseString;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.keyboardplaying.mapper.converter.Converter#convertFromString(java.lang.String)
     */
    @Override
    public Boolean convertFromString(String value) throws ConversionException {
        Boolean result;
        if (this.whenTrue.equalsIgnoreCase(value)) {
            result = true;
        } else if (this.whenFalse.equalsIgnoreCase(value)) {
            result = false;
        } else {
            throw new ConversionException("Value <" + value
                    + "> could not be parsed to boolean (authorized: " + this.whenTrue + "/"
                    + this.whenFalse + ")");
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.keyboardplaying.mapper.converter.Converter#convertToString(java.lang.Object)
     */
    @Override
    public String convertToString(Boolean bool) {
        return bool != null && bool.booleanValue() ? this.whenTrue : this.whenFalse;
    }
}
