package org.keyboardplaying.mapper.parser;

import org.keyboardplaying.mapper.Defaults;
import org.keyboardplaying.mapper.exception.ParsingException;

/**
 * Implementation of {@link SimpleParser} for {@code Boolean} to {@code String} parsing.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class BooleanParser implements SimpleParser<Boolean> {

    /** The {@link String} representation of {@code true}. */
    private String[] whenTrue = { Defaults.BOOLEAN_YES };
    /** The {@link String} representation of {@code false}. */
    private String[] whenFalse = { Defaults.BOOLEAN_NO };

    /**
     * Sets the values to use for {@code true} and {@code false} values.
     *
     * @param trueStrings
     *            the {@link String} representations of {@code true}
     * @param falseStrings
     *            the {@link String} representations of {@code false}
     */
    public void setTrueFalse(String[] trueStrings, String[] falseStrings) {
        this.whenTrue = checkArray(trueStrings, "The array of true representations should contain at least one value.");
        this.whenFalse = checkArray(falseStrings,
                "The array of false representations should contain at least one value.");
    }

    private String[] checkArray(String[] array, String message) {
        if (array == null || array.length < 1) {
            throw new IllegalArgumentException(message);
        }
        return array;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.SimpleParser#convertFromString(java.lang.String)
     */
    @Override
    public Boolean convertFromString(String value) throws ParsingException {
        Boolean result;
        if (containsIgnoreCase(whenTrue, value)) {
            result = true;
        } else if (containsIgnoreCase(whenFalse, value)) {
            result = false;
        } else {
            throw new ParsingException("Value <" + value + "> could not be parsed to boolean (authorized: "
                    + this.whenTrue + "/" + this.whenFalse + ")");
        }
        return result;
    }

    private boolean containsIgnoreCase(String[] array, String value) {
        for (String val : array) {
            if (val.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.SimpleParser#convertToString(java.lang.Object)
     */
    @Override
    public String convertToString(Boolean bool) {
        return (bool.booleanValue() ? this.whenTrue : this.whenFalse)[0];
    }
}
