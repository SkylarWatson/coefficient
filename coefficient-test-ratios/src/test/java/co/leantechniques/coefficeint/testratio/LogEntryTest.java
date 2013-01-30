package co.leantechniques.coefficeint.testratio;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class LogEntryTest {
    @Test
    public void calculatesTestRatio() {
        LogEntry entry = new LogEntry(5, 3, 2);
        assertEquals(66, entry.getTestRatio());
    }

}
