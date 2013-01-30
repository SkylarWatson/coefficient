package co.leantechniques.coefficient.scm.mercurial;

import co.leantechniques.coefficient.core.SystemClock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HgCommandLineBuilderTest {

    private HgCommandLineBuilder commandLineBuilder;

    @Before
    public void setUp() throws Exception {
        commandLineBuilder = new HgCommandLineBuilder();
    }

    @Test
    public void noMerges() throws Exception {
        Assert.assertThat(commandLineBuilder.getCommandLineArguments(), JUnitMatchers.hasItem("--no-merge"));
    }

    @Test
    public void contains10DaysFilter() throws Exception {
        Date january11 = new GregorianCalendar(2012, Calendar.JANUARY, 11).getTime();
        SystemClock.setNow(january11);

        commandLineBuilder.setRangeLimitInDays(10);
        Assert.assertThat(commandLineBuilder.getCommandLineArguments(), JUnitMatchers.hasItem("--date"));
        Assert.assertThat(commandLineBuilder.getCommandLineArguments(), JUnitMatchers.hasItem("2012-01-01 to 2012-01-11"));
    }

    @Test
    public void contains5DayFilter() throws Exception {
        Date january6 = new GregorianCalendar(2012, Calendar.JANUARY, 6).getTime();
        SystemClock.setNow(january6);

        commandLineBuilder.setRangeLimitInDays(5);

        Assert.assertThat(commandLineBuilder.getCommandLineArguments(), JUnitMatchers.hasItem("--date"));
        Assert.assertThat(commandLineBuilder.getCommandLineArguments(), JUnitMatchers.hasItem("2012-01-01 to 2012-01-06"));
    }

    @Test
    public void containsTemplate() throws Exception {
        Assert.assertThat(commandLineBuilder.getCommandLineArguments(), JUnitMatchers.hasItem("--template"));
    }

    @Test
    public void containsTemplateFormat() throws Exception {
        Assert.assertThat(commandLineBuilder.getCommandLineArguments(), JUnitMatchers.hasItem("{author|user}||{desc}||{files}\\n"));
    }

}
