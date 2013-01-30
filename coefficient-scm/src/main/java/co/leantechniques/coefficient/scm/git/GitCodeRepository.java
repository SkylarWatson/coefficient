package co.leantechniques.coefficient.scm.git;

import co.leantechniques.coefficient.scm.CodeRepository;
import co.leantechniques.coefficient.core.CommandLineAdaptor;
import co.leantechniques.coefficient.scm.Commit;
import co.leantechniques.coefficient.scm.WorkingDirectory;

import java.util.Arrays;
import java.util.Set;

public class GitCodeRepository implements CodeRepository {
    private WorkingDirectory workingDirectory;
    private int numberOfDays;

    public GitCodeRepository(WorkingDirectory workingDirectory, int numberOfDays) {
        this.workingDirectory = workingDirectory;
        this.numberOfDays = numberOfDays;
    }

    @Override
    public Set<Commit> getCommits() {
        GitCommitCapturer commitCapturer = new GitCommitCapturer();
        CommandLineAdaptor adaptor = new CommandLineAdaptor();
        adaptor.execute(workingDirectory.getPath(), Arrays.asList("git", "log", "--name-only", "--since='" + numberOfDays + " days ago'"), commitCapturer);
        return commitCapturer.getCommits();
    }
}
