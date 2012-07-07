package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class SystemClockTest {

    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() throws Exception {
        setCurrentDateToJanuary10();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    public void getCurrentDateWhenSet() throws Exception {
        assertThat(SystemClock.getCurrentDate(), equalTo(getCurrentDate()));
    }

    @Test
    public void add() throws Exception {
        Date fiveDaysLater = SystemClock.addDays(5);

        assertThat(dateFormat.format(fiveDaysLater), equalTo("2012-01-15"));
    }

    private void setCurrentDateToJanuary10() {
        Date currentDate = getCurrentDate();
        SystemClock.setNow(currentDate);
    }

    private Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, Calendar.JANUARY, 10);
        return calendar.getTime();
    }

}
