package utilities;

import java.sql.Time;
import java.util.Calendar;

public class DateTimeManager {

    private DateTimeManager () {}

    public static java.sql.Date toSqlDate(java.util.Date dataJava) {
        return new java.sql.Date(dataJava.getTime());
    }

    public static java.util.Date toJavaDate(java.sql.Date sqlDate) {
        return new java.util.Date(sqlDate.getTime());
    }

    public static Time toTimeByCalendar(int yyyy, int mm, int dd, int h, int m, int s) {
        Calendar c = Calendar.getInstance();
        c.set(yyyy, mm, dd, h, m, s);
        return new Time(c.getTimeInMillis());
    }

    public static java.util.Date toDateByCalendar(int yyyy, int mm, int dd) {
        Calendar c = Calendar.getInstance();
        c.set(yyyy, mm, dd);
        return c.getTime();
    }
}
