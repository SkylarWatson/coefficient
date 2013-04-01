package co.leantechniques.coefficient.scm;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commit {
    private static final String UNKNOWN_STORY = "Unknown";
    private static final List<String> SOURCE_EXTENSIONS = Arrays.asList("java");
    private final String author;
    private String message;

    private final Set<String> files;
    private final Set<String> testSourceFiles;
    private final Set<String> productionSourceFiles;

    public Commit(String author, String message, String... files) {
        this.author = author;
        this.files = new HashSet<String>(Arrays.asList(files));
        this.message = message;
        this.productionSourceFiles = new HashSet<String>();
        this.testSourceFiles = new HashSet<String>();

        for (String filename : this.files) {
            FilenameAttributes file = attributesOf(filename);
                if (file.isSource) {
                    if (file.isProduction) {
                        this.productionSourceFiles.add(filename);
                    } else {
                        this.testSourceFiles.add(filename);
                    }
                }
        }
    }

    private FilenameAttributes attributesOf(String filename) {
        String[] components = new File(filename).getName().split("\\.");

        return new FilenameAttributes(SOURCE_EXTENSIONS.contains(fileExtensionFrom(components)), isProduction(components[0]));
    }

    private boolean isProduction(String component) {
        return !component.endsWith("Test");
    }

    private String fileExtensionFrom(String[] components) {
        return components[components.length-1];
    }

    private class FilenameAttributes {
        public final boolean isSource;
        public final boolean isProduction;
        public final boolean isTest;

        public FilenameAttributes(boolean source, boolean production) {
            isSource = source;
            isProduction = production;
            isTest = !production;
        }
    }

    public String getStory() {
        if (message == null)
            return UNKNOWN_STORY;

        Matcher matcher = Pattern.compile("(US|DE)\\d+").matcher(message);

        return matcher.find() ? matcher.group() : UNKNOWN_STORY;
    }

    public Set<String> getFiles() {
        return files;
    }

    public String getAuthor() {
        return author;
    }

    public boolean containsTests() {
        return !testSourceFiles.isEmpty();
    }

    public boolean containsProductionCode() {
        return !getProductionSourceFiles().isEmpty();
    }

    public Set<String> getProductionSourceFiles() {
        return productionSourceFiles;
    }

    public Set<String> getTestSourceFiles() {
        return testSourceFiles;
    }
}
