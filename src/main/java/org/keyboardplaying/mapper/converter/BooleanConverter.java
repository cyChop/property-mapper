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

import org.apache.commons.lang.BooleanUtils;
import org.keyboardplaying.mapper.exception.ConversionException;
import org.keyboardplaying.mapper.utils.Defaults;

/**
 * Implementation of {@link Converter} for {@code Boolean} to {@code String}
 * conversion.
 * 
 * 
 * @author cyChop (http://keyboardplaying.org/)
 */
public class BooleanConverter implements Converter<Boolean> {

    /** The {@link String} representation of {@code true}. */
    private String whenTrue = Defaults.BOOLEAN_TRUE;
    /** The {@link String} representation of {@code false}. */
    private String whenFalse = Defaults.BOOLEAN_FALSE;

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
     * @see
     * org.keyboardplaying.mapper.converter.Converter#convertFromString(java
     * .lang.String)
     */
    @Override
    public Boolean convertFromString(String value) throws ConversionException {
        Boolean result;
        if (this.whenTrue.equalsIgnoreCase(value)) {
            result = true;
        } else if (this.whenFalse.equalsIgnoreCase(value)) {
            result = false;
        } else {
            throw new ConversionException("Value <" + value + "> could not be parsed to boolean (authorized: "
                    + this.whenTrue + "/" + this.whenFalse + ")");
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.keyboardplaying.mapper.converter.Converter#convertToString(java.lang
     * .Object)
     */
    @Override
    public String convertToString(Boolean value) {
        return BooleanUtils.isTrue(value) ? this.whenTrue : this.whenFalse;
    }
}
