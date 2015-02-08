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
package org.keyboardplaying.mapper.annotation;

import org.keyboardplaying.mapper.utils.Defaults;

/**
 * This enum lists the available time representations in the metadata mapping.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public enum TemporalType {
    /** For date values, not including the notion of time. */
    DATE(Defaults.FORMAT_DATE),
    /** For timestamp values, including both date and time. */
    DATETIME(Defaults.FORMAT_DATETIME);

    /** The format of the string representation of the date. */
    private String format;

    /**
     * Private constructor.
     *
     * @param format
     *            the format to use when parsing the {@link String} representation of a time or
     *            formatting the time to a {@link String}
     */
    private TemporalType(String format) {
        this.format = format;
    }

    /**
     * Returns the format to use when parsing the {@link String} representation of a time or
     * formatting the time to a {@link String}.
     *
     * @return the format to use when parsing between time objects and their {@link String}
     *         representation
     */
    public String getFormat() {
        return format;
    }
}
