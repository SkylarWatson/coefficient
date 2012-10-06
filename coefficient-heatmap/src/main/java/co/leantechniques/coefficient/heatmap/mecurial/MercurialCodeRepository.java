package co.leantechniques.coefficient.heatmap.mecurial;

import co.leantechniques.coefficient.heatmap.CodeRepository;
import co.leantechniques.coefficient.heatmap.Commit;
import co.leantechniques.coefficient.heatmap.CommitLogParser;
import co.leantechniques.coefficient.heatmap.WorkingDirectory;

import java.util.Set;

public class MercurialCodeRepository implements CodeRepository {

    CommitLogParser logParser = new CommitLogParser();
    private HgLogCommand logCommand = new HgLogCommand();
    private WorkingDirectory workingDirectory;
    private int rangeLimitInDays;

    public MercurialCodeRepository(WorkingDirectory workingDirectory, int rangeLimitInDays) {
        this.workingDirectory = workingDirectory;
        this.rangeLimitInDays = rangeLimitInDays;
    }

    @Override
    public Set<Commit> getCommits() {
        return logParser.getCommits(logCommand.execute(workingDirectory.getPath(), rangeLimitInDays));
    }
}