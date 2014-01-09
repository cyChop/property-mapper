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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.keyboardplaying.mapper.annotation.Temporal;
import org.keyboardplaying.mapper.exception.ConversionException;

/**
 * @author cyChop (http://keyboardplaying.org/)
 */
public class DateAndTimeConverterTest {

    private DateConverter dateTimeConv = new DateConverter();
    private DateConverter dateConv = new DateConverter();
    private CalendarConverter dateTimeCalConv = new CalendarConverter();
    private CalendarConverter dateCalConv = new CalendarConverter();

    private String sDateTimestamp = "1985/10/24-21:20:42";
    private String sDate = "2012/06/29";

    private Calendar dateTime;
    private Calendar date;

    @Before
    public void prepareDates() {
        dateTimeConv.setFormat(Temporal.Type.DATETIME.getFormat());
        dateConv.setFormat(Temporal.Type.DATE.getFormat());
        dateTimeCalConv.setFormat(Temporal.Type.DATETIME.getFormat());
        dateCalConv.setFormat(Temporal.Type.DATE.getFormat());

        dateTime = Calendar.getInstance();
        dateTime.set(Calendar.YEAR, 1985);
        dateTime.set(Calendar.MONTH, Calendar.OCTOBER);
        dateTime.set(Calendar.DAY_OF_MONTH, 24);
        dateTime.set(Calendar.HOUR_OF_DAY, 21);
        dateTime.set(Calendar.MINUTE, 20);
        dateTime.set(Calendar.SECOND, 42);
        dateTime.set(Calendar.MILLISECOND, 0);

        date = Calendar.getInstance();
        date.set(Calendar.YEAR, 2012);
        date.set(Calendar.MONTH, Calendar.JUNE);
        date.set(Calendar.DAY_OF_MONTH, 29);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
    }

    @Test
    public void testConvertStringToDateTimestamp() throws ConversionException {
        Date now = new Date();

        assertEquals(dateTime.getTime(), dateTimeConv.convertFromString(sDateTimestamp, now));
        assertEquals(dateTime.getTime(), dateTimeConv.convertFromString(sDateTimestamp, null));
        assertEquals(now, dateTimeConv.convertFromString(null, now));
        assertNull(dateTimeConv.convertFromString(null, null));
        try {
            dateTimeConv.convertFromString(sDate, now);
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
        try {
            dateTimeConv.convertFromString("notADate", now);
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertDateTimestampToString() {
        assertEquals(sDateTimestamp, dateTimeConv.convertToString(dateTime.getTime(), date.getTime()));
        assertEquals(sDateTimestamp, dateTimeConv.convertToString(dateTime.getTime(), null));
        assertEquals(sDate + "-00:00:00", dateTimeConv.convertToString(null, date.getTime()));
        assertNull(dateTimeConv.convertToString(null, null));
    }

    @Test
    public void testConvertStringToDate() throws ConversionException {
        Date now = new Date();

        assertEquals(date.getTime(), dateConv.convertFromString(sDate, now));
        assertEquals(date.getTime(), dateConv.convertFromString(sDate, null));
        assertEquals(now, dateConv.convertFromString(null, now));
        assertNull(dateConv.convertFromString(null, null));
        try {
            dateConv.convertFromString("notADate", now);
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertDateToString() {
        assertEquals(sDate, dateConv.convertToString(date.getTime(), dateTime.getTime()));
        assertEquals(sDate, dateConv.convertToString(date.getTime(), null));
        assertEquals(sDateTimestamp.substring(0, 10), dateConv.convertToString(null, dateTime.getTime()));
        assertNull(dateConv.convertToString(null, null));
    }

    @Test
    public void testConvertStringToCalendarTimestamp() throws ConversionException {
        Calendar now = Calendar.getInstance();

        assertEquals(dateTime, dateTimeCalConv.convertFromString(sDateTimestamp, now));
        assertEquals(dateTime, dateTimeCalConv.convertFromString(sDateTimestamp, null));
        assertEquals(now, dateTimeCalConv.convertFromString(null, now));
        assertNull(dateTimeCalConv.convertFromString(null, null));
        try {
            dateTimeCalConv.convertFromString(sDate, now);
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
        try {
            dateTimeCalConv.convertFromString("notADate", now);
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertCalendarTimestampToString() {
        assertEquals(sDateTimestamp, dateTimeCalConv.convertToString(dateTime, date));
        assertEquals(sDateTimestamp, dateTimeCalConv.convertToString(dateTime, null));
        assertEquals(sDate + "-00:00:00", dateTimeCalConv.convertToString(null, date));
        assertNull(dateTimeCalConv.convertToString(null, null));
    }

    @Test
    public void testConvertStringToCalendar() throws ConversionException {
        Calendar now = Calendar.getInstance();

        assertEquals(date, dateCalConv.convertFromString(sDate, now));
        assertEquals(date, dateCalConv.convertFromString(sDate, null));
        assertEquals(now, dateCalConv.convertFromString(null, now));
        assertNull(dateCalConv.convertFromString(null, null));
        try {
            dateCalConv.convertFromString("notADate", now);
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertCalendarToString() {
        assertEquals(sDate, dateCalConv.convertToString(date, dateTime));
        assertEquals(sDate, dateCalConv.convertToString(date, null));
        assertEquals(sDateTimestamp.substring(0, 10), dateCalConv.convertToString(null, dateTime));
        assertNull(dateCalConv.convertToString(null, null));
    }
}
