package co.leantechniques.coefficient.heatmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangesetAnalyzer {
    private final CodeRepository codeRepository;
    private final String defectDiscriminator;

    public ChangesetAnalyzer(CodeRepository codeRepository, String defectDiscriminator) {
        this.codeRepository = codeRepository;
        this.defectDiscriminator = defectDiscriminator;
    }

    public boolean fileExists(Map<String, FileStatistics> r, String file) {
        return r.containsKey(file);
    }

    public boolean isDefect(String story) {
        return Pattern.compile(defectDiscriminator).matcher(story).find();
    }

    public Map<String, Set<String>> getFilesByStory() {
        Set<Commit> commits = codeRepository.getCommits();
        HashMap<String, Set<String>> mapStoryToChangedFiles = new HashMap<String, Set<String>>();
        for (Commit commit : commits) {
            if (mapStoryToChangedFiles.containsKey(commit.getStory()))
                mapStoryToChangedFiles.get(commit.getStory()).addAll(commit.getFiles());
            else
                mapStoryToChangedFiles.put(commit.getStory(), commit.getFiles());
        }
        return mapStoryToChangedFiles;
    }

    public AuthorStatisticSet getAuthorStatistics() {
        Set<Commit> commits = codeRepository.getCommits();
        AuthorStatisticSet authorStatisticSet = new AuthorStatisticSet();
        for (Commit commit : commits) {
            authorStatisticSet.getCommitStatisticForAuthor(commit.getAuthor()).incrementTotalCommits();
            authorStatisticSet.incrementTotalCommits();
            if (commit.containsTests()) {
                authorStatisticSet.incrementTestedCommits();
                authorStatisticSet.getCommitStatisticForAuthor(commit.getAuthor()).incrementTestedCommits();
            }
        }
        return authorStatisticSet;
    }

    Map<String, FileStatistics> changesPerFile() {
        Map<String, Set<String>> filesByStory = getFilesByStory();
        Map<String, FileStatistics> numberOfChangesOrganizedByFile = new HashMap<String, FileStatistics>();
        boolean isDefect;
        for (String story : filesByStory.keySet()) {
            isDefect = isDefect(story);
            for (String filename : filesByStory.get(story)) {
                if (filename.endsWith(".java")) {
                    if (fileExists(numberOfChangesOrganizedByFile, filename)) {
                        FileStatistics fileStatistics = numberOfChangesOrganizedByFile.get(filename);
                        update(fileStatistics, isDefect);
                    } else {
                        numberOfChangesOrganizedByFile.put(filename, new FileStatistics(1, isDefect ? 1 : 0));
                    }
                }
            }
        }
        return numberOfChangesOrganizedByFile;
    }

    private void update(FileStatistics fileStatistics, boolean defect) {
        fileStatistics.recordChange();
        if (defect) {
            fileStatistics.markAsDefect();
        }
    }
}