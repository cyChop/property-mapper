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

import java.util.Date;

import org.keyboardplaying.mapper.exception.ConversionException;

/**
 * Implementation of {@link Converter} for {@code Date} to {@code String}
 * conversion.
 * 
 * @author cyChop (http://keyboardplaying.org/)
 */
public class DateConverter extends BaseTemporalConverter<Date> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.keyboardplaying.mapper.converter.Converter#convertFromString(java
     * .lang.String)
     * 
     * @see org.keyboardplaying.mapper.converter.BaseTemporalConverter#
     * convertStringToDate(java.lang.String)
     */
    @Override
    public Date convertFromString(String value) throws ConversionException {
        return convertStringToDate(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.keyboardplaying.mapper.converter.Converter#convertToString(java.lang
     * .Object)
     * 
     * @see org.keyboardplaying.mapper.converter.BaseTemporalConverter#
     * convertDateToString(java.util.Date)
     */
    @Override
    public String convertToString(Date value) {
        return convertDateToString(value);
    }
}
