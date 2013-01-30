package co.leantechniques.coefficient.scm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commit {
    private String message;
    private final Set<String> files;
    private final String author;

    public Commit(String author, String message, String... files) {
        this.author = author;
        this.files = new HashSet<String>(Arrays.asList(files));
        this.message = message;
    }

    public String getStory() {
        if(message == null) return "Unknown";
        Matcher matcher = Pattern.compile("(US|DE)\\d+").matcher(message);
        boolean wasFound = matcher.find();
        if (wasFound)
            return matcher.group();
        else
            return "Unknown";
    }

    public Set<String> getFiles() {
        return files;
    }

    public String getAuthor() {
        return author;
    }

    public boolean containsTests() {
        return getPercentFilesWIthTests() > 0;
    }

    public int getPercentFilesWIthTests() {
        Pattern p = Pattern.compile(".*/(Test)?(\\w+?)(Test)?\\.(\\w+)");
        double numberOfSourceFilesChanged = 0;
        double numberOfUnitTests = 0;
        for(String file : files) {
            Matcher m = p.matcher(file);
            if(!m.matches())
                continue;
            if(isSourceFile(m)) {
                numberOfSourceFilesChanged++;
                if(isTestFile(m)) {
                    numberOfUnitTests++;
                }
            }
        }

        double numberOfProductionClasses = ((int) numberOfSourceFilesChanged - (int) numberOfUnitTests);

        if(numberOfProductionClasses == 0)
            return 100;

        return (int) (((numberOfUnitTests / (int) numberOfProductionClasses) * 100));
    }

    private boolean isTestFile(Matcher m) {
        return m.group(1) != null || m.group(3) != null;
    }

    private boolean isSourceFile(Matcher m) {
        return m.group(4).equals("java");
    }
}
