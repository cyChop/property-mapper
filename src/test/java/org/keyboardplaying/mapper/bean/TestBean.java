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
package org.keyboardplaying.mapper.bean;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.keyboardplaying.mapper.annotation.BooleanValues;
import org.keyboardplaying.mapper.annotation.DefaultValue;
import org.keyboardplaying.mapper.annotation.Metadata;
import org.keyboardplaying.mapper.annotation.Nested;
import org.keyboardplaying.mapper.annotation.Temporal;
import org.keyboardplaying.mapper.annotation.TemporalType;

/**
 * @author cyChop (http://keyboardplaying.org/)
 */
public class TestBean {

    @Metadata("hello_world")
    @DefaultValue("Did not say hello... :(")
    private String hello;

    @Metadata("some_bool")
    @BooleanValues(whenTrue = "true", whenFalse = "false")
    private boolean someBool;

    @Metadata("some_number")
    private int someInt;

    @Metadata("some_number")
    private Long someLong;

    @Metadata("some_number")
    private BigInteger someBig;

    @Metadata("some_even_more_important_date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Metadata("some_important_date")
    @Temporal(TemporalType.DATETIME)
    private Calendar cal;

    @Nested
    private TestInnerImpl innerImpl;

    @Nested(className = "org.keyboardplaying.mapper.bean.TestInnerImpl")
    private TestInnerBean innerItf;

    @Metadata(value = "somebody_s_name", customSetter = "setContact")
    private String contact;

    public TestBean() {
        super();
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    public boolean isSomeBool() {
        return someBool;
    }

    public void setSomeBool(boolean someBool) {
        this.someBool = someBool;
    }

    public int getSomeInt() {
        return someInt;
    }

    public void setSomeInt(int someInt) {
        this.someInt = someInt;
    }

    public Long getSomeLong() {
        return someLong;
    }

    public void setSomeLong(Long someLong) {
        this.someLong = someLong;
    }

    public BigInteger getSomeBig() {
        return someBig;
    }

    public void setSomeBig(BigInteger someBig) {
        this.someBig = someBig;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public TestInnerImpl getInnerImpl() {
        return innerImpl;
    }

    public void setInnerImpl(TestInnerImpl innerImpl) {
        this.innerImpl = innerImpl;
    }

    public TestInnerBean getInnerItf() {
        return innerItf;
    }

    public void setInnerItf(TestInnerBean innerItf) {
        this.innerItf = innerItf;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setContact(String name, Map<String, String> metadata) {
        this.contact = String.format("%s (%s)", name, metadata.get("somebody_s_phone"));
    }
}
