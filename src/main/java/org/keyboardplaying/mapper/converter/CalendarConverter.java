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
    @Override
    public Calendar convertFromString(String value) throws ConversionException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertStringToDate(value));
        return calendar;
    }

    /**
     * Converts a {@link Calendar} instance to a {@link String} representation.
     * <p/>
     * The {@link String} will conform to the format specified for this
     * {@link BaseTemporalConverter}.
     * 
     * @param value
     *            the {@link Calendar}
     * @return a {@link String} representation of the supplied date
     * @throws ConversionException
     *             when the supplied {@link String} could not be parsed
     * @see {@link BaseTemporalConverter#convertDateToString(String)}
     */
    @Override
    public String convertToString(Calendar value) {
        return convertDateToString(value.getTime());
    }
}