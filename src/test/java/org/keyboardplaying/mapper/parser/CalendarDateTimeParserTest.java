package org.keyboardplaying.mapper.parser;

import java.util.Calendar;

import org.keyboardplaying.mapper.annotation.Temporal.TemporalType;

/**
 * {@link TemporalParserTest} implementation for {@link CalendarParser} for datetimes.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class CalendarDateTimeParserTest extends TemporalParserTest<Calendar> {

    /**
     * Creates a new instance.
     */
    public CalendarDateTimeParserTest() {
        super(makeParser(), makeExpected(), true);
    }

    private static CalendarParser makeParser() {
        CalendarParser parser = new CalendarParser();
        parser.setFormat(TemporalType.DATETIME.getFormat());
        return parser;
    }

    private static Calendar makeExpected() {
        Calendar dateTime = Calendar.getInstance();
        dateTime.set(Calendar.YEAR, 1985);
        dateTime.set(Calendar.MONTH, Calendar.OCTOBER);
        dateTime.set(Calendar.DAY_OF_MONTH, 24);
        dateTime.set(Calendar.HOUR_OF_DAY, 21);
        dateTime.set(Calendar.MINUTE, 20);
        dateTime.set(Calendar.SECOND, 42);
        dateTime.set(Calendar.MILLISECOND, 0);

        return dateTime;
    }
}
