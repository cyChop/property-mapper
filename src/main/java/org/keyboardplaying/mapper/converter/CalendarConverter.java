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

import java.util.Calendar;

import org.keyboardplaying.mapper.exception.ConversionException;

/**
 * Implementation of {@link Converter} for {@code Calendar} to {@code String}
 * conversion.
 *
 * @author cyChop (http://keyboardplaying.org/)
 */
public class CalendarConverter extends BaseTemporalConverter<Calendar> {

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.Converter#convertFromString
     * (java.lang.String, java.lang.Object)
     */
    public Calendar convertFromString(String value, Calendar def) throws ConversionException {
        return value == null ? def : convertStringToCalendar(value);
    }

    /**
     * Converts a {@link String} representation of a date to a {@link Calendar}
     * instance.
     * <p/>
     * The {@link String} is expected to conform to the format specified for
     * this {@link BaseTemporalConverter}.
     *
     * @param value
     *            the {@link String} representation of a date
     * @return a {@link Calendar} instance
     * @throws ConversionException
     *             when the supplied {@link String} could not be parsed
     * @see {@link BaseTemporalConverter#convertStringToDate(String)}
     */
    private Calendar convertStringToCalendar(String value) throws ConversionException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertStringToDate(value));
        return calendar;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.Converter#convertToString(
     * java.lang.Object, java.lang.String)
     */
    public String convertToString(Calendar value, Calendar def) {
        String result = null;
        if (value != null) {
            result = convertDateToString(value.getTime());
        } else if (def != null) {
            result = convertDateToString(def.getTime());
        }
        return result;
    }
}
