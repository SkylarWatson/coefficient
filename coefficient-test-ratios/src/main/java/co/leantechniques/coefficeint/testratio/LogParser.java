package co.leantechniques.coefficeint.testratio;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {

    private final String commitDelimiter;
    private final String sourceFileExtension;

    public LogParser(String commitDelimiter, String sourceFileExtension) {
        this.commitDelimiter = commitDelimiter;
        this.sourceFileExtension = sourceFileExtension;
    }

    public List<LogEntry> execute(String scmLogText) {
        ArrayList<LogEntry> entries = new ArrayList<LogEntry>();

        String[] commits = scmLogText.split(commitDelimiter);
        for (String commit : commits) {
            String[] allFilesInThisCommit = commit.split(" ");
            int numberOfSourceFiles = 0;
            int numberOfUnitTestFiles = 0;

            for (String file : allFilesInThisCommit) {
                Matcher m = Pattern.compile(".*/(Test)?(\\w+?)(Test)?\\.(\\w+)").matcher(file);
                m.matches();

                if (fileIsSourceCode(m)) {
                    numberOfSourceFiles++;

                    if (fileIsUnitTest(m)) {
                        numberOfUnitTestFiles++;
                    }
                }
            }
            entries.add(new LogEntry(allFilesInThisCommit.length, numberOfSourceFiles, numberOfUnitTestFiles));
        }
        return entries;
    }

    private boolean fileIsSourceCode(Matcher m) {
        return m.group(4).equals(sourceFileExtension);
    }

    private boolean fileIsUnitTest(Matcher m) {
        return (m.group(1) != null || m.group(3) != null);
    }

}
