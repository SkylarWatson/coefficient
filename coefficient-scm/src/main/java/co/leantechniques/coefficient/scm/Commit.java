package co.leantechniques.coefficient.scm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commit {
    public static final Pattern SOURCE_FILENAME_PATTERN = Pattern.compile(".*/(\\w+?)(Test)?\\.(\\w+)");
    private String message;
    private final Set<String> files;
    private final String author;

    public Commit(String author, String message, String... files) {
        this.author = author;
        this.files = new HashSet<String>(Arrays.asList(files));
        this.message = message;
    }

    public String getStory() {
        if (message == null) return "Unknown";
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
        for (String file : files) {
            Matcher m = Pattern.compile(".*/(\\w+?)(Test)?\\.(\\w+)").matcher(file);
            if (!m.matches())
                continue;

            if (isTestFile(m)) return true;
        }
        return false;
    }

    public int getPercentFilesWIthTests() {
        HashSet<String> productionFiles = new HashSet<String>();
        HashSet<String> testFiles = new HashSet<String>();
        for (String file : files) {
            Matcher m = SOURCE_FILENAME_PATTERN.matcher(file);
            if (!m.matches())
                continue;
            String baseName = m.group(1);
            if (isSourceFile(m)) {
                if (isTestFile(m)) {
                    testFiles.add(baseName);
                } else {
                    productionFiles.add(baseName);
                }
            }
        }

        if (productionFiles.isEmpty()) {
            throw new IllegalStateException("No production classes found in this commit!");
        }

        return ratioAsPercentage(testFiles.size(), productionFiles.size());
    }

    private int ratioAsPercentage(double numberOfTestFiles, double numberOfProductionFiles) {
        double ratio = numberOfTestFiles / numberOfProductionFiles;
        return toWholeNumber(ratio);
    }

    private int toWholeNumber(double ratio) {
        return (int) (ratio * 100);
    }

    private boolean isTestFile(Matcher m) {
        return m.group(2) != null;
    }

    private boolean isSourceFile(Matcher m) {
        return m.group(3).equals("java");
    }

    public boolean containsProductionCode() {
        for (String file : files) {
            Matcher m = SOURCE_FILENAME_PATTERN.matcher(file);
            if (!m.matches())
                continue;
            if (isSourceFile(m)) {
                if (!isTestFile(m)) {
                    return true;
                }
            }
        }

        return false;
    }
}
