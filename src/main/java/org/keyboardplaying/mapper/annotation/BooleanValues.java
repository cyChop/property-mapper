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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.keyboardplaying.mapper.utils.Defaults;

/**
 * Defines the {@link String} representations for {@code true} and {@code false} when dealing with
 * boolean metadata.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BooleanValues {

    /**
     * The {@link String} representation of a {@code true} boolean.
     *
     * @return the {@link String} representation of a {@code true} boolean.
     */
    String whenTrue() default Defaults.BOOLEAN_TRUE;

    /**
     * The {@link String} representation of a {@code false} boolean.
     *
     * @return the {@link String} representation of a {@code false} boolean.
     */
    String whenFalse() default Defaults.BOOLEAN_FALSE;
}
