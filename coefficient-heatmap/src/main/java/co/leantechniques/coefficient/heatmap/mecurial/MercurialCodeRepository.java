package co.leantechniques.coefficient.heatmap.mecurial;

import co.leantechniques.coefficient.heatmap.CodeRepository;
import co.leantechniques.coefficient.heatmap.Commit;
import co.leantechniques.coefficient.heatmap.CommitLogParser;
import co.leantechniques.coefficient.heatmap.LogCommand;

import java.util.Set;

public class MercurialCodeRepository implements CodeRepository {

    CommitLogParser logParser = new CommitLogParser();
    private LogCommand logCommand = new HgLogCommand();
    private int rangeLimitInDays;

    // Used for reflection from AdapterFactory
    public MercurialCodeRepository(int rangeLimitInDays) {
        this.rangeLimitInDays = rangeLimitInDays;
    }

    @Override
    public Set<Commit> getCommits() {
        return logParser.getCommits(logCommand.execute(rangeLimitInDays));
    }
}