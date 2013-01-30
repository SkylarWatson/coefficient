package co.leantechniques.coefficeint.testratio;

import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class CommitsWithTestRatioTest {
    @Test
    public void retrievesHistoryFromScmAndPassesItToTheParser() {
        CommitsWithTestRatio ap = new CommitsWithTestRatio();
        ScmAdapter scmAdapter = mock(ScmAdapter.class);
        LogParser parser = mock(LogParser.class);
        when(scmAdapter.log()).thenReturn("scm changeset data");
        when(parser.execute("scm changeset data")).thenReturn(Arrays.asList(new LogEntry(10, 5, 5), new LogEntry(10, 5, 0)));
        ap.setScmAdapter(scmAdapter);
        ap.setLogParser(parser);
        MyListener listener = mock(MyListener.class);
        ap.setListener(listener);

        ap.process();

        verify(scmAdapter).log();
        verify(parser).execute("scm changeset data");
        verify(listener).onCommitAnalysisComplete(50);
    }

}
