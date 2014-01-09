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

import org.keyboardplaying.mapper.exception.ConversionException;

/**
 * Implementation of {@link Converter} for {@code Integer} to {@code String}
 * conversion.
 *
 * @author cyChop (http://keyboardplaying.org/)
 */
public class IntegerConverter extends BaseConverter<Integer> {

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.Converter#convertFromString
     * (java .lang.String, java.lang.Object)
     */
    public Integer convertFromString(String value, Integer def) throws ConversionException {
        try {
            return value == null ? def : Integer.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new ConversionException("Value <" + value + "> could not be parsed to integer", e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.converter.Converter#convertToString(
     * java.lang.Object, java.lang.Object)
     */
    public String convertToString(Integer value, Integer def) {
        return value == null ? def == null ? null : def.toString() : value.toString();
    }
}
