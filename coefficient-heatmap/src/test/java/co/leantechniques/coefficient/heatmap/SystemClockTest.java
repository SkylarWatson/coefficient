package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SystemClockTest {

    public static final SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setUp() throws Exception {
        SystemClock.setNow(january(10, 2012));
    }

    @Test
    public void getCurrentDateWhenSet() throws Exception {
        Date date = january(10, 2012);
        SystemClock.setNow(date);

        assertThat(SystemClock.getCurrentDate(), is(sameInstance(date)));
    }

    @Test
    public void add() throws Exception {
        Date fiveDaysLater = SystemClock.addDays(5);

        assertThat(pretty(fiveDaysLater), equalTo("2012-01-15"));
    }

    private String pretty(Date fiveDaysLater) {
        return YYYY_MM_DD.format(fiveDaysLater);
    }

    private Date january(int day, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, day);
        return calendar.getTime();
    }

}
