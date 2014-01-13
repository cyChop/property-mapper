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
 * This annotation allows for settings a default value when the source value is
 * {@code null} or not defined.
 * <p/>
 * In the mapping sense, the default value will be set to the defined
 * {@link Metadata} field as is. In case of an unmapping, the value set to the
 * bean's field will be the result from converting the default value.
 * <p/>
 * This has been made a separate annotation to allow for the empty
 * {@link String} default value.
 *
 * @author cyChop (http://keyboardplaying.org/)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DefaultValue {

    /**
     * Returns the default value of the field.
     *
     * @return the default value for the field
     */
    String value();
}
