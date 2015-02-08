/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keyboardplaying.mapper.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.keyboardplaying.mapper.exception.ConversionException;

/**
 * This implementation of {@link Converter} provides some utility methods when parsing time
 * representations.
 * <p/>
 * Using this implies to use {@link Date} objects at least as intermediary steps. You might wish to
 * use more direct ways if it is possible.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 * @param <T>
 *            the type of time objects this {@link Converter} converts from and to
 */
public abstract class BaseTemporalConverter<T> implements TemporalConverter<T> {

    /** The format to use for the string representation of timestamps. */
    private DateFormat format;

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.TemporalConverter#setFormat(java .lang.String)
     */
    @Override
    public void setFormat(String format) {
        this.format = new SimpleDateFormat(format);
    }

    /**
     * Converts a {@link String} representation of a date to a {@link Date} instance.
     * <p/>
     * The {@link String} is expected to conform to the format specified for this
     * {@link BaseTemporalConverter}.
     *
     * @param value
     *            the {@link String} representation of a date
     * @return a {@link Date} instance
     * @throws ConversionException
     *             when the supplied {@link String} could not be parsed
     */
    protected Date convertStringToDate(String value) throws ConversionException {
        try {
            return format.parse(value);
        } catch (ParseException e) {
            throw new ConversionException("Value <" + value
                    + "> could not be parsed to date using format <" + format + ">.");
        }
    }

    /**
     * Converts a {@link Date} instance to a {@link String} representation.
     * <p/>
     * The {@link String} will conform to the format specified for this
     * {@link BaseTemporalConverter}.
     *
     * @param value
     *            the {@link Date}
     * @return a {@link String} representation of the supplied date
     * @throws ConversionException
     *             when the supplied {@link String} could not be parsed
     */
    protected String convertDateToString(Date value) {
        return format.format(value);
    }
}
