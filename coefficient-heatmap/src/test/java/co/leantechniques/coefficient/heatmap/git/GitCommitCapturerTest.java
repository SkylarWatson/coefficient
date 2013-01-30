package co.leantechniques.coefficient.heatmap.git;

import co.leantechniques.coefficient.scm.Commit;
import co.leantechniques.coefficient.scm.git.GitCommitBuilder;
import co.leantechniques.coefficient.scm.git.GitCommitCapturer;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GitCommitCapturerTest {
    private GitCommitCapturer commitCapturer;

    @Before
    public void setUp() throws Exception {
        commitCapturer = new GitCommitCapturer();
    }

    @Test
    public void shouldCaptureAllTheCommitsInTheLog() {
        GitCommitBuilder commit = new GitCommitBuilder().author("Joe Blow").message("US123 statement").file("src/com/blah.Blah.java");
        GitCommitBuilder otherCommit = new GitCommitBuilder().author("Jim Bob").message("US321 statement").file("src/com/blah/Foo.java");
        Set<Commit> commits = processCommitLog(commit, otherCommit);

        assertEquals(2, commits.size());
        Iterator<Commit> iterator = commits.iterator();
        assertCommit(iterator.next(), "Joe Blow", "US123", "src/com/blah.Blah.java");
        assertCommit(iterator.next(), "Jim Bob", "US321", "src/com/blah/Foo.java");
    }

    @Test
    public void shouldCaptureAllLinesOfTheCommitMessage() {
        Set<Commit> commits = processCommitLog(new GitCommitBuilder().author("Joe Blow").message("US123 statement", "US321 other statement").file("src/com/blah.Blah.java"));

        assertEquals(1, commits.size());
        assertCommit(commits.iterator().next(), "Joe Blow", "US123", "src/com/blah.Blah.java");
    }

    @Test
    public void shouldCreateACommitObjectForASingleCommit() {
        Set<Commit> commits = processCommitLog(new GitCommitBuilder().author("Joe Blow").message("US123 statement").file("src/com/blah/Blah.java"));

        assertNotNull(commits);
        assertEquals(1, commits.size());
        Commit commit = commits.iterator().next();
        assertCommit(commit, "Joe Blow", "US123", "src/com/blah/Blah.java");
    }


    private void assertCommit(Commit commit, String expectedAuthor, String expectedStory, String... expectedFiles) {
        assertEquals("Authors do not match", expectedAuthor, commit.getAuthor());
        assertEquals("Files in the commit do not match", new HashSet(Arrays.asList(expectedFiles)), commit.getFiles());
        assertEquals("Stories do not match", expectedStory, commit.getStory());
    }

    private Set<Commit> processCommitLog(GitCommitBuilder... commits) {
        for (int i = 0; i < commits.length; i++) {
            GitCommitBuilder commit = commits[i];
            for (String logLine : commit.toLines()) {
                commitCapturer.add(logLine);
            }

            if (i < commits.length - 1) {
                commitCapturer.add("");
            }
        }
        return commitCapturer.getCommits();
    }
}
