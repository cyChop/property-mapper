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

/**
 * Implementation of {@link Converter} for {@code String} to {@code String}
 * conversion.
 * <p/>
 * No change is made except in case of a default value.
 *
 * @author cyChop (http://keyboardplaying.org/)
 */
public class StringConverter extends BaseConverter<String> {

    /*
     * (non-Javadoc)
     *
     * @see
     * org.keyboardplaying.mapper.converter.Converter#convertFromString(java
     * .lang.String, java.lang.Object)
     */
    public String convertFromString(String value, String def) {
        return valueOrDefault(value, def);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.keyboardplaying.mapper.converter.Converter#convertToString(java.lang
     * .Object, java.lang.String)
     */
    public String convertToString(String value, String def) {
        return valueOrDefault(value, def);
    }

    /**
     * Returns the value or a default value if the value is {@code null}.
     *
     * @param value
     *            the value
     * @param def
     *            the default value
     * @return the value or a default one if the first is {@code null}
     */
    private String valueOrDefault(String value, String def) {
        return value == null ? def : value;
    }
}
