package org.keyboardplaying.mapper.parser;

import java.util.Calendar;

import org.keyboardplaying.mapper.annotation.TemporalType;

/**
 * {@link TemporalParserTest} implementation for {@link CalendarParser} for dates.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class CalendarDateParserTest extends TemporalParserTest<Calendar> {

    /** Creates a new instance. */
    public CalendarDateParserTest() {
        super(makeParser(), makeExpected(), false);
    }

    private static CalendarParser makeParser() {
        CalendarParser parser = new CalendarParser();
        parser.setFormat(TemporalType.DATE.getFormat());
        return parser;
    }

    private static Calendar makeExpected() {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, 2012);
        date.set(Calendar.MONTH, Calendar.JUNE);
        date.set(Calendar.DAY_OF_MONTH, 29);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        return date;
    }
}
