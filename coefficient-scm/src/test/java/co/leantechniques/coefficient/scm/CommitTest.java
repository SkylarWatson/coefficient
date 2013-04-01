package co.leantechniques.coefficient.scm;

import co.leantechniques.coefficient.core.Environment;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Set;

import static junit.framework.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
    public void knowsWhichFilesAreProduction() {
        Commit commit = new Commit(null, null, "com/example/File1.java",
                "com/example/File1Test.java",
                "com/example/File2.java",
                "com/example/File2Test.java",
                "com/example/File3.java");

        Set<String> changes = commit.getProductionSourceFiles();

        assertEquals(3, changes.size());
        assertTrue(changes.contains("com/example/File1.java"));
        assertTrue(changes.contains("com/example/File2.java"));
        assertTrue(changes.contains("com/example/File3.java"));
    }

    @Test
    public void knowsWhichFilesAreTests() {
        Commit commit = new Commit(null, null, "com/example/File1.java",
                "com/example/File1Test.java",
                "com/example/File2.java",
                "com/example/File2Test.java",
                "com/example/File3.java");

        Set<String> changes = commit.getTestSourceFiles();

        assertEquals(2, changes.size());
        assertTrue(changes.contains("com/example/File1Test.java"));
        assertTrue(changes.contains("com/example/File2Test.java"));
    }

    @Test
    public void supportsFilesInTheCurrentDirectory() {
        Commit commitWithTest = new Commit(null, null, "pom.xml");
        assertFalse(commitWithTest.containsProductionCode());
    }

    @Test
    public void supportsFilesWithNestedPeriodsInTheFilename() {
        Commit commitWithTest = new Commit(null, null, "help-files.tar.gz");
        assertFalse(commitWithTest.containsProductionCode());
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
