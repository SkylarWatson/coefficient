package co.leantechniques.coefficient.scm.mercurial;

import co.leantechniques.coefficient.scm.CodeRepository;
import co.leantechniques.coefficient.scm.Commit;
import co.leantechniques.coefficient.scm.CommitLogParser;
import co.leantechniques.coefficient.scm.WorkingDirectory;

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