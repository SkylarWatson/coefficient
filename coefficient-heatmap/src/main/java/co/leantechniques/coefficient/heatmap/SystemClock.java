package co.leantechniques.coefficient.heatmap;

import java.util.Calendar;
import java.util.Date;

public class SystemClock {
    private static Date currentDate;

    public static Date getCurrentDate(){
        if(currentDate != null) return currentDate;
        return new Date();
    }

    public static void setNow(Date currentDate) {
        SystemClock.currentDate = currentDate;
    }

    public static Date addDays(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getCurrentDate());
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
}
