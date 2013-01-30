package co.leantechniques.coefficeint.testratio;

import java.util.List;

public class CommitsWithTestRatio {
    private ScmAdapter scmAdapter;
    private MyListener listener;
    private LogParser logParser;

    public void setScmAdapter(ScmAdapter scmAdapter) {
        this.scmAdapter = scmAdapter;
    }

    public void setListener(MyListener listener) {
        this.listener = listener;
    }

    public void process() {
        String d = scmAdapter.log();
        List<LogEntry> logEntries = logParser.execute(d);
        int total = 0;
        for(LogEntry e : logEntries) {
            total += e.getTestRatio();
        }
        total /= logEntries.size();

        listener.onCommitAnalysisComplete(total);

    }

    public void setLogParser(LogParser logParser) {
        this.logParser = logParser;
    }
}
