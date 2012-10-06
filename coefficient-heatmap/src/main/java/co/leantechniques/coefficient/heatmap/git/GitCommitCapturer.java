package co.leantechniques.coefficient.heatmap.git;

import co.leantechniques.coefficient.heatmap.CommandLineListener;
import co.leantechniques.coefficient.heatmap.Commit;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GitCommitCapturer extends CommandLineListener {
    private List<Commit> commits = new ArrayList<Commit>();
    private String currentAuthor;
    private StringBuilder commitMessage = new StringBuilder();
    private CommitState currentState;
    private List<String> files = new ArrayList<String>();

    @Override
    public void add(String line) {
        if (isStartOfACommit(line)) {
            currentState = CommitState.AUTHOR;
        } else if (currentState == CommitState.AUTHOR) {
            currentAuthor = parseAuthorFrom(line);
            currentState = CommitState.DATE;
        } else if (isDateOfCommit(line)) {
            // skip the date line
        } else if (currentState == CommitState.DATE) {
            currentState = CommitState.MESSAGE;
        } else if (currentState == CommitState.MESSAGE) {
            if (isBlankLine(line)) {
                currentState = CommitState.FILES;
            } else {
                commitMessage.append(line);
            }
        } else if (currentState == CommitState.FILES) {
            if (isBlankLine(line)) {
                captureCurrentCommit();
            } else {
                files.add(line);
            }
        }
    }

    private void finished() {
        captureCurrentCommit();
    }

    private void captureCurrentCommit() {
        commits.add(new Commit(currentAuthor, commitMessage.toString(), files.toArray(new String[files.size()])));
        commitMessage = new StringBuilder();
        files.clear();
    }

    private boolean isBlankLine(String line) {
        return line.trim().length() == 0;
    }

    private boolean isDateOfCommit(String line) {
        return line.startsWith("Date:");
    }

    private String parseAuthorFrom(String line) {
        return line.replaceAll("Author: (.+) <.+", "$1");
    }

    public Set<Commit> getCommits() {
        finished();
        return new LinkedHashSet<Commit>(commits);
    }

    private boolean isStartOfACommit(String line) {
        return line.matches("commit .+");
    }

    private static enum CommitState {
        AUTHOR, DATE, MESSAGE, FILES
    }
}
