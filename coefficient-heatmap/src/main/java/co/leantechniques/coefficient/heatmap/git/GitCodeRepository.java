package co.leantechniques.coefficient.heatmap.git;

import co.leantechniques.coefficient.heatmap.CodeRepository;
import co.leantechniques.coefficient.heatmap.CommandLineAdaptor;
import co.leantechniques.coefficient.heatmap.Commit;
import co.leantechniques.coefficient.heatmap.WorkingDirectory;

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
