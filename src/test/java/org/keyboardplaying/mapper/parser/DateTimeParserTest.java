package org.keyboardplaying.mapper.parser;

import java.util.Calendar;
import java.util.Date;

import org.keyboardplaying.mapper.annotation.Temporal.TemporalType;

/**
 * {@link TemporalParserTest} implementation for {@link DateParser} for datetimes.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class DateTimeParserTest extends TemporalParserTest<Date> {

    /** Creates a new instance. */
    public DateTimeParserTest() {
        super(makeParser(), makeExpected(), true);
    }

    private static DateParser makeParser() {
        DateParser parser = new DateParser();
        parser.setFormat(TemporalType.DATETIME.getFormat());
        return parser;
    }

    private static Date makeExpected() {
        Calendar dateTime = Calendar.getInstance();
        dateTime.set(Calendar.YEAR, 1985);
        dateTime.set(Calendar.MONTH, Calendar.OCTOBER);
        dateTime.set(Calendar.DAY_OF_MONTH, 24);
        dateTime.set(Calendar.HOUR_OF_DAY, 21);
        dateTime.set(Calendar.MINUTE, 20);
        dateTime.set(Calendar.SECOND, 42);
        dateTime.set(Calendar.MILLISECOND, 0);

        return dateTime.getTime();
    }
}
