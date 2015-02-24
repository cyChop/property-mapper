package org.keyboardplaying.mapper.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.keyboardplaying.mapper.annotation.TemporalType;
import org.keyboardplaying.mapper.exception.ConversionException;

// XXX Javadoc
/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
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
        dateTimeConv.setFormat(TemporalType.DATETIME.getFormat());
        dateConv.setFormat(TemporalType.DATE.getFormat());
        dateTimeCalConv.setFormat(TemporalType.DATETIME.getFormat());
        dateCalConv.setFormat(TemporalType.DATE.getFormat());

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
    public void testConvertStringToDateTimestamp() throws ConversionException, ParseException {
        assertEquals(dateTime.getTime(), dateTimeConv.convertFromString(sDateTimestamp));
        try {
            dateTimeConv.convertFromString(sDate);
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
        try {
            dateTimeConv.convertFromString("notADate");
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertDateTimestampToString() throws ConversionException {
        assertEquals(sDateTimestamp, dateTimeConv.convertToString(dateTime.getTime()));
    }

    @Test
    public void testConvertStringToDate() throws ConversionException, ParseException {
        assertEquals(date.getTime(), dateConv.convertFromString(sDate));
        try {
            dateConv.convertFromString("notADate");
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertDateToString() throws ConversionException {
        assertEquals(sDate, dateConv.convertToString(date.getTime()));
    }

    @Test
    public void testConvertStringToCalendarTimestamp() throws ConversionException, ParseException {
        assertEquals(dateTime, dateTimeCalConv.convertFromString(sDateTimestamp));
        try {
            dateTimeCalConv.convertFromString(sDate);
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
        try {
            dateTimeCalConv.convertFromString("notADate");
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertCalendarTimestampToString() throws ConversionException {
        assertEquals(sDateTimestamp, dateTimeCalConv.convertToString(dateTime));
    }

    @Test
    public void testConvertStringToCalendar() throws ConversionException, ParseException {
        assertEquals(date, dateCalConv.convertFromString(sDate));
        try {
            dateCalConv.convertFromString("notADate");
            fail();
        } catch (ConversionException e) {
            // do nothing, this is expected
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConvertCalendarToString() throws ConversionException {
        assertEquals(sDate, dateCalConv.convertToString(date));
    }
}
