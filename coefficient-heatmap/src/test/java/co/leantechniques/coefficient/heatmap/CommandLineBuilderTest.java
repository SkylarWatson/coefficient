package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

public class CommandLineBuilderTest {

    private CommandLineBuilder commandLineBuilder;

    @Before
    public void setUp() throws Exception {
        commandLineBuilder = new CommandLineBuilder();
    }

    @Test
    public void noMerges() throws Exception {
        assertThat(commandLineBuilder.getCommandLineArguments(), hasItem("--no-merge"));
    }

    @Test
    public void contains10DaysFilter() throws Exception {
        Date january11 = new GregorianCalendar(2012, Calendar.JANUARY, 11).getTime();
        SystemClock.setNow(january11);

        commandLineBuilder.setRangeLimitInDays(10);
        assertThat(commandLineBuilder.getCommandLineArguments(), hasItem("--date"));
        assertThat(commandLineBuilder.getCommandLineArguments(), hasItem("2012-01-01 to 2012-01-11"));
    }

    @Test
    public void contains5DayFilter() throws Exception {
        Date january6 = new GregorianCalendar(2012, Calendar.JANUARY, 6).getTime();
        SystemClock.setNow(january6);

        commandLineBuilder.setRangeLimitInDays(5);

        assertThat(commandLineBuilder.getCommandLineArguments(), hasItem("--date"));
        assertThat(commandLineBuilder.getCommandLineArguments(), hasItem("2012-01-01 to 2012-01-06"));
    }

    @Test
    public void containsTemplate() throws Exception {
        assertThat(commandLineBuilder.getCommandLineArguments(), hasItem("--template"));
    }

    @Test
    public void containsTemplateFormat() throws Exception {
        assertThat(commandLineBuilder.getCommandLineArguments(), hasItem("{author|user}||{desc}||{files}\\n"));
    }

}
