package co.leantechniques.coefficient.core;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class SystemClockTest {

    @Before
    public void setClock() throws Exception {
        SystemClock.setNow(january(10, 2012));
    }

    @Test
    public void getCurrentDateWhenSet() throws Exception {
        assertThat(SystemClock.getCurrentDate(), equalTo(january(10, 2012)));
    }

    @Test
    public void add() throws Exception {
        Date fiveDaysLater = SystemClock.addDays(5);
        assertThat(fiveDaysLater, equalTo(january(15, 2012)));
    }

    private Date january(int day, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0L);
        calendar.set(year, Calendar.JANUARY, day);
        return calendar.getTime();
    }

}
