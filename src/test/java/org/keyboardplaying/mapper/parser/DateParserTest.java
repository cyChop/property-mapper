package org.keyboardplaying.mapper.parser;

import java.util.Calendar;
import java.util.Date;

import org.keyboardplaying.mapper.annotation.TemporalType;

/**
 * {@link TemporalParserTest} implementation for {@link DateParser} for dates.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class DateParserTest extends TemporalParserTest<Date> {

    /** Creates a new instance. */
    public DateParserTest() {
        super(makeParser(), makeExpected(), false);
    }

    private static DateParser makeParser() {
        DateParser parser = new DateParser();
        parser.setFormat(TemporalType.DATE.getFormat());
        return parser;
    }

    private static Date makeExpected() {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, 2012);
        date.set(Calendar.MONTH, Calendar.JUNE);
        date.set(Calendar.DAY_OF_MONTH, 29);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        return date.getTime();
    }
}
