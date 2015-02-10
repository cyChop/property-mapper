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
package org.keyboardplaying.mapper.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.InvocationTargetException;

/**
 * A test class to test the project's exceptions' constructors work as intended.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
abstract class ExceptionTest<T extends Exception> {

    private Class<T> klass;

    protected ExceptionTest(Class<T> klass) {
        this.klass = klass;
    }

    protected void testNoArgConstructor() throws InstantiationException, IllegalAccessException {
        T exception = klass.newInstance();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    protected void testMessageConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        String message = "Hello, world!";
        T exception = klass.getConstructor(String.class).newInstance(message);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    protected void testCauseConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        Throwable cause = new IllegalArgumentException("He's dead, Jim!");
        T exception = klass.getConstructor(Throwable.class).newInstance(cause);
        assertEquals(cause.toString(), exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    protected void testBothConstructor() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        String message = "Hello, world!";
        Throwable cause = new IllegalArgumentException("He's dead, Jim!");
        T exception = klass.getConstructor(String.class, Throwable.class).newInstance(message,
                cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
