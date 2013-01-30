package co.leantechniques.coefficient.scm;

import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class CommitLogParserTest {

    private CommitLogParser logParser;

    @Before
    public void setUp() throws Exception {
        logParser = new CommitLogParser();
    }

    @Test
    public void createFromString(){
        Commit actual = logParser.getFirstCommit("tim||US1234 some message||File1.java");

        assertThat(actual.getAuthor(), is("tim"));
        assertThat(actual.getStory(), is("US1234"));
        assertThat(actual.getFiles(), hasSize(1));
    }

    @Test
    public void createWhenDescriptionContainsNewLines(){
        Commit actual = logParser.getFirstCommit("tim||US1234 Message with" + Environment.getLineSeparator() + "embedded newline||File1.java");
        
        assertThat(actual.getAuthor(), is("tim"));
        assertThat(actual.getStory(), is("US1234"));
        assertThat(actual.getFiles(), hasSize(1));
    }

    @Test
    public void createWithoutStory(){
        Commit actual = logParser.getFirstCommit("tim||Message without story||File1.java");

        assertThat(actual.getStory(), is("Unknown"));
    }

    @Test
    public void createForMergeCommitWithoutFiles(){
        Commit actual = logParser.getFirstCommit("tim||US1234 Message without files||");

        assertThat(actual.getAuthor(), is("tim"));
        assertThat(actual.getStory(), is("US1234"));
        assertThat(actual.getFiles(), hasSize(0));
    }

    @Test
    public void createMultipleCommitsWhenLineSeparated(){
        String logMessageStream = getCommitLog(
                "tim||US1234 some message||File1.java",
                "tim||US1234 some message||File1.java",
                "tim||US1234 some message||File1.java");

        Set<Commit> actualCommits = logParser.getCommits(logMessageStream);

        assertThat(actualCommits, hasSize(3));
    }

    private String getCommitLog(String... commits) {
        String commitData = "";
        for (String commit : commits) {
            commitData += commit + Environment.getLineSeparator();
        }
        return commitData;
    }
}
