package co.leantechniques.coefficient.scm;

import co.leantechniques.coefficient.core.Environment;
import org.junit.Test;

import java.util.Set;

import static junit.framework.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CommitTest {
    @Test
    public void knowsWhatStoryItsFor() {
        assertEquals("US1234", new Commit(null, "US1234 Some message").getStory());
    }

    @Test
    public void admitsItWhenItCantFigureOutWhatStory() {
        assertEquals("Unknown", new Commit(null, "Message without a story").getStory());
    }

    @Test
    public void knowsWhatFilesAreContainedWithinTheCommit() {
        Set<String> filesCommitted = new Commit(null, null, "File1.java", "File2.java", "File3.java").getFiles();

        assertEquals(3, filesCommitted.size());

        assertTrue(filesCommitted.contains("File1.java"));
        assertTrue(filesCommitted.contains("File2.java"));
        assertTrue(filesCommitted.contains("File3.java"));
    }
    
    @Test
    public void knowsWhenCommitContainsATest(){
        Commit commitWithTest = new Commit(null, null, "com/example/File1.java", "com/example/File1Test.java");
        
        assertTrue(commitWithTest.containsTests());
    }

    @Test
    public void knowsWhenCommitNotTested(){
        Commit commit = new Commit(null, null, "com/example/File1.java");

        assertFalse(commit.containsTests());
    }

    @Test
    public void knowsWhenCommitHasOnlyTests(){
        Commit commit = new Commit(null, null, "com/example/File1Test.java");

        assertTrue(commit.containsTests());
    }

    @Test
    public void knowsWhenCommitContainsProductionCode() {
        Commit commit = new Commit(null, null, "com/example/File1Test.java");

        assertFalse(commit.containsProductionCode());

    }

    @Test
    public void knowsThePercentageOfSourceFilesThatHaveTests() {
        Commit commit = new Commit(null, null, "com/example/File1.java",
                "com/example/File1Test.java",
                "com/example/File2.java",
                "com/example/File2Test.java",
                "com/example/File3.java");

        assertEquals(66, commit.getPercentFilesWIthTests());
    }

    @Test
    public void onlyConsidersSourceFilesInCalculations() {
        Commit commit = new Commit(null, null, "com/example/File1.java",
                "com/example/File1Test.java",
                "com/example/File2.properties",
                "com/example/File3.xml",
                "pom.xml");

        assertEquals(100, commit.getPercentFilesWIthTests());
    }

    @Test                                            (expected=IllegalStateException.class)
    public void considersCommitsThatContainsOnlyTests() {
        Commit commit = new Commit(null, null, "com/example/File1Test.java");

        assertEquals(100, commit.getPercentFilesWIthTests());
    }

    @Test
    public void getStoryHandlesNulls(){
        Commit commitWithTest = new Commit(null, null, "File1.java");

        assertThat(commitWithTest.getStory(), notNullValue());
    }

    @Test
    public void isntConfusedByEmbeddedNewlines() throws Exception {
        String descriptionWithNewLines = "US1234 Some hokey Message".replaceAll(" ", Environment.getLineSeparator());
        Commit commitWithTest = new Commit("joesmith", descriptionWithNewLines, "File1.java");

        assertThat(commitWithTest.getAuthor(), equalTo("joesmith"));
        assertThat(commitWithTest.getStory(), equalTo("US1234"));
    }
}
