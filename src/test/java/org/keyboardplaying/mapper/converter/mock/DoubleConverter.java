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
package org.keyboardplaying.mapper.converter.mock;

import org.keyboardplaying.mapper.converter.BaseConverter;
import org.keyboardplaying.mapper.exception.ConversionException;

/**
 * @author cyChop (http://keyboardplaying.org/)
 */
public class DoubleConverter extends BaseConverter<Double> {

    /*
     * (non-Javadoc)
     *
     * @see
     * org.keyboardplaying.mapper.converter.Converter#convertFromString(java
     * .lang.String, java.lang.Object)
     */
    public Double convertFromString(String value, Double def) throws ConversionException {
        return value == null ? def : Double.parseDouble(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.keyboardplaying.mapper.converter.Converter#convertToString(java.lang
     * .Object, java.lang.Object)
     */
    public String convertToString(Double value, Double def) throws ConversionException {
        return value == null ? def == null ? null : def.toString() : value.toString();
    }
}
