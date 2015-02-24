package org.keyboardplaying.mapper.mock.converter;

import org.keyboardplaying.mapper.converter.Converter;
import org.keyboardplaying.mapper.exception.ConversionException;

// XXX Javadoc
/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class DoubleConverter implements Converter<Double> {

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.Converter#convertFromString(java .lang.String)
     */
    @Override
    public Double convertFromString(String value) throws ConversionException {
        return Double.parseDouble(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.Converter#convertToString(java.lang .Object)
     */
    @Override
    public String convertToString(Double value) throws ConversionException {
        return value.toString();
    }
}
