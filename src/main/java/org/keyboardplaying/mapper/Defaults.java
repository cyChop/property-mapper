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
package org.keyboardplaying.mapper;

/**
 * Some default values.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public final class Defaults {

    /** The default date format. */
    public static final String FORMAT_DATE = "yyyy/MM/dd";
    /** The default timestamp format. */
    public static final String FORMAT_DATETIME = "yyyy/MM/dd-HH:mm:ss";

    /** The {@link String} representation for {@code true}. */
    public static final String BOOLEAN_YES = "YES";
    /** The {@link String} representation for {@code false}. */
    public static final String BOOLEAN_NO = "NO";
    /** An alternate {@link String} representation for {@code true}. */
    public static final String BOOLEAN_YES_FR = "OUI";
    /** An alternate {@link String} representation for {@code false}. */
    public static final String BOOLEAN_NO_FR = "NON";

    /** Private constructor to avoid instantiation. */
    private Defaults() {
    }
}
