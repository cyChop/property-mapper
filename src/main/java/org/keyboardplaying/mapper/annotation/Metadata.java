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

/**
 * This annotation marks a field that should be mapped between map and bean. It should be used to
 * map the bean's field with the name of its counterpart in the map as a value.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Metadata {

    /**
     * Returns the key for the value in the map which should be used when setting this field.
     *
     * @return the key for this field's value in the metadata map
     */
    String value();

    /**
     * Defines whether a field is mandatory.
     * <p/>
     * If the field is mandatory but missing when mapping (or missing in the map when unmapping), an
     * exception will be thrown.
     *
     * @return {@code true} if this field is mandatory, {@code false} otherwise
     */
    boolean mandatory() default false;

    /**
     * The name of a custom setter to use when unmapping this field.
     * <p/>
     * The signature of the setter should be
     * {@code setSomething(java.lang.String, java.util.Map<String, String>)}. It will receive the
     * value for the required metadata as the first argument, and the whole metadata map as the
     * second.
     *
     * @return the name of the custom setter to use when unmapping
     */
    String customSetter() default "";

    /**
     * The name of a custom getter to use when mapping this field.
     * <p/>
     * This getter should return a {@link String} representation of the field.
     *
     * @return the name of the custom getter to use when mapping
     */
    String customGetter() default "";
}
