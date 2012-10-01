package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertTrue;

public class HtmlRendererTest {

    private Map<String, FileStatistics> changes;

    @Before
    public void setUp() {
        changes = new TreeMap<String, FileStatistics>();
    }

    @Test
    public void generatesTheCloudInHtml() {
        initializeFileStatistics("AbstractChainOfResponsibilityFactory.java", 1, 0);
        assertMatches("<html><head>(.*)</head><body><ol>(.*)</ol></body></html>", render(changes));
    }

    private void initializeFileStatistics(String filename, int totalNumberOfChanges, int totalNumberOfDefects) {
        changes.put(filename, new FileStatistics(totalNumberOfChanges, totalNumberOfDefects));
    }

    @Test
    public void ordersTheTagCloudBasedOnFilename() {
        initializeFileStatistics("AbstractChainOfResponsibilityFactory.java", 1, 0);
        initializeFileStatistics("ZumbaTraining.java", 1, 0);
        initializeFileStatistics("BasicChainOfResponsibilityFactory.java", 1, 0);

        assertMatches(".*AbstractChainOfResponsibilityFactory.*BasicChainOfResponsibilityFactory.*ZumbaTraining.*", render(changes));
    }

    @Test
    public void rendersEachFileAsATag() {
        initializeFileStatistics("ChangeSet.java", 1, 0);

        assertMatches("<li .+>ChangeSet</li>", render(changes));
    }

    @Test
    public void onlyShowsBaseFilenameForEachTag() {
        initializeFileStatistics("src/main/java/com/example/ChangeSet.java", 1, 0);

        assertMatches("<li(.+)>ChangeSet</li>", render(changes));
    }

    @Test
    public void addsFileDetailsToTheTitleAttributeForDisplayWhenHoveredOver() {
        initializeFileStatistics("src/main/java/com/example/ChangeSet.java", 1, 0);

        assertMatches("title='src/main/java/com/example/ChangeSet.java -> Changes: 1  Defects: 0'", render(changes));
    }

    @Test
    public void adjustsTheFontSizeOfEachTagRelativeToTheNumberOfChangesInTheFile() {
        initializeFileStatistics("src/main/java/com/example/NotChangedOften.java", 1, 0);
        initializeFileStatistics("src/main/java/com/example/ChangedMoreOften.java", 6, 0);
        initializeFileStatistics("src/main/java/com/example/AlwaysChanging.java", 12, 0);

        String html = render(changes);

        assertMatches("font-size: 6", html);
        assertMatches("font-size: 18", html);
        assertMatches("font-size: 36", html);
    }

    @Test
    public void adjustsTheFontSizeRelativeToTheTotalNumberOfChanges() {
        initializeFileStatistics("src/main/java/com/example/NotChangedOften.java", 10, 0);
        initializeFileStatistics("src/main/java/com/example/ChangedMoreOften.java", 15, 0);
        initializeFileStatistics("src/main/java/com/example/AlwaysChanging.java", 20, 0);

        String html = render(changes);

        assertMatches("font-size: 6", html);
        assertMatches("font-size: 21", html);
        assertMatches("font-size: 36", html);
    }

    @Test
    public void adjustsTheColorOfEachTagBasedOnTheNumberOfChangesThatWereDefects() {
        initializeFileStatistics("src/main/java/com/example/NoDefects.java", 10, 0);
        initializeFileStatistics("src/main/java/com/example/MinimalDefects.java", 15, 3);
        initializeFileStatistics("src/main/java/com/example/ManyDefects.java", 20, 15);

        String html = render(changes);

        assertMatches("211,211,211", html);
        assertMatches("51,0,0", html);
        assertMatches("191,0,0", html);
    }

    private void assertMatches(String pattern, String target) {
        assertTrue(assertionMessage(pattern, target), target.matches(".*" + pattern + ".*"));
    }

    private String assertionMessage(String pattern, String target) {
        return "Expected <" + target + "> to match <" + pattern + ">";
    }

    private String render(Map<String, FileStatistics> changes) {
        return new HtmlRenderer(changes).render();
    }
}
