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
package org.keyboardplaying.mapper.engine;

import java.lang.reflect.Field;

import org.keyboardplaying.mapper.annotation.BooleanValues;
import org.keyboardplaying.mapper.annotation.Temporal;
import org.keyboardplaying.mapper.converter.BooleanConverter;
import org.keyboardplaying.mapper.converter.Converter;
import org.keyboardplaying.mapper.converter.ConverterProvider;
import org.keyboardplaying.mapper.converter.TemporalConverter;
import org.keyboardplaying.mapper.exception.MappingException;

// XXX JAVADOC
// XXX Study the opportunity of creating a MappingExceptionFactory
/**
 * @author cyChop (http://keyboardplaying.org/)
 */
public abstract class MappingEngine {

    private ConverterProvider converterProvider = new ConverterProvider();

    /**
     * Returns the appropriate {@link Converter} based on the supplied's field
     * type.
     * 
     * @param field
     *            the field to convert a value from or to
     * @return
     * @throws MappingException
     *             when no {@link Converter} can be found or annotation settings
     *             are missing (e.g. {@link Temporal} on temporal fields)
     */
    protected <T> Converter<T> getConverter(Field field) throws MappingException {
        Converter<T> converter = converterProvider.getConverter(field.getType());

        if (converter == null) {

            throw new MappingException("No converter could be found for type " + field.getType() + " (field "
                    + field.getName() + " of " + field.getDeclaringClass().getName() + ")");

        } else if (converter instanceof TemporalConverter) {

            if (field.isAnnotationPresent(Temporal.class)) {
                Temporal temporal = field.getAnnotation(Temporal.class);
                ((TemporalConverter<T>) converter).setFormat(temporal.value().getFormat());
            } else {
                throw new MappingException("Field " + field.getName() + " of " + field.getDeclaringClass().getName()
                        + " must declare the @Temporal annotation.");
            }

        } else if (converter instanceof BooleanConverter && field.isAnnotationPresent(BooleanValues.class)) {

            BooleanValues annot = field.getAnnotation(BooleanValues.class);
            ((BooleanConverter) converter).setTrueFalse(annot.whenTrue(), annot.whenFalse());
        }

        return converter;
    }
}
