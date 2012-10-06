package co.leantechniques.coefficient.heatmap.mecurial;

import co.leantechniques.coefficient.heatmap.*;

import java.util.Set;

public class MercurialCodeRepository implements CodeRepository {

    CommitLogParser logParser = new CommitLogParser();
    private LogCommand logCommand = new HgLogCommand();
    private WorkingDirectory workingDirectory;
    private int rangeLimitInDays;

    public MercurialCodeRepository(WorkingDirectory workingDirectory, int rangeLimitInDays) {
        this.workingDirectory = workingDirectory;
        this.rangeLimitInDays = rangeLimitInDays;
    }

    @Override
    public Set<Commit> getCommits() {
        return logParser.getCommits(logCommand.execute(rangeLimitInDays));
    }
}