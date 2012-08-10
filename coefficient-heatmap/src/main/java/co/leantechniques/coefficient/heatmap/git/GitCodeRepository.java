package co.leantechniques.coefficient.heatmap.git;

import co.leantechniques.coefficient.heatmap.CodeRepository;
import co.leantechniques.coefficient.heatmap.CommandLineAdaptor;
import co.leantechniques.coefficient.heatmap.Commit;

import java.util.Arrays;
import java.util.Set;

public class GitCodeRepository implements CodeRepository {
    private int numberOfDays;

    public GitCodeRepository(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    @Override
    public Set<Commit> getCommits() {
        GitCommitCapturer commitCapturer = new GitCommitCapturer();
        CommandLineAdaptor adaptor = new CommandLineAdaptor();
        adaptor.execute(Arrays.asList("git", "log", "--name-only", "--since='" + numberOfDays + " days ago'"), commitCapturer);
        return commitCapturer.getCommits();
    }
}
