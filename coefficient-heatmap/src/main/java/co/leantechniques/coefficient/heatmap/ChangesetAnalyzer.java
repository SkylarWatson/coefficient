package co.leantechniques.coefficient.heatmap;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangesetAnalyzer {
    private final CodeRepository codeRepository;

    public ChangesetAnalyzer(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public static boolean fileExists(Map<String, HeatmapData> r, String file) {
        return r.containsKey(file);
    }

    public static void initializeCount(Map<String, HeatmapData> r, String file, boolean isDefect) {
        HeatmapData heatmapdata = new HeatmapData();
        heatmapdata.changes = 1;
        if (isDefect) {
            heatmapdata.defects = 1;
        }
        else{
            heatmapdata.defects = 0;
        }
        r.put(file, heatmapdata);
    }

    public static boolean isDefect(String story) {
        Matcher matcher = Pattern.compile("DE\\d+").matcher(story);
        return matcher.find();
    }

    public static void incrementChangeCountForFile(Map<String, HeatmapData> r, String file, boolean isDefect) {
        HeatmapData heatmapData = r.get(file);
        heatmapData.incrementCounters(isDefect);
    }

    public Map<String, Set<String>> getFilesByStory() {
        Set<Commit> commits = codeRepository.getCommits();
        HashMap<String, Set<String>> mapStoryToChangedFiles = new HashMap<String, Set<String>>();
        for(Commit commit : commits){
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
        for(Commit commit : commits){
            authorStatisticSet.getCommitStatisticForAuthor(commit.getAuthor()).incrementTotalCommits();
            authorStatisticSet.incrementTotalCommits();
            if(commit.containsTests()){
                authorStatisticSet.incrementTestedCommits();
                authorStatisticSet.getCommitStatisticForAuthor(commit.getAuthor()).incrementTestedCommits();
            }
        }
        return authorStatisticSet;
    }

    //    public Map<String, Set<Commit>> commitStatisticsByAuthor(Iterable<Commit> commits) {
//        HashMap<String, Set<Commit>> commitStatisticsByAuthor = new HashMap<String, Set<Commit>>();
//        for(Commit commit : commits){
//            if (commitStatisticsByAuthor.containsKey(commit.getAuthor()))
//                commitStatisticsByAuthor.get(commit.getAuthor()).add(commit);
//            else {
//                Set<Commit> commitSet = new HashSet<Commit>();
//                commitSet.add(commit);
//                commitStatisticsByAuthor.put(commit.getAuthor(), commitSet);
//            }
//        }
//        return commitStatisticsByAuthor;
//    }

    Map<String, HeatmapData> changesPerFile() {
        Map<String, Set<String>> filesByStory = getFilesByStory();
        Map<String, HeatmapData> numberOfChangesOrganizedByFile = new HashMap<String, HeatmapData>();
        boolean isDefect;
        for (String story : filesByStory.keySet()) {
            isDefect = isDefect(story);
            for (String file : filesByStory.get(story)) {
                if(file.endsWith(".java")){
//                if (includes.contains(GetFileExtension(file))) {
                    if (fileExists(numberOfChangesOrganizedByFile, file)) {
                        incrementChangeCountForFile(numberOfChangesOrganizedByFile, file, isDefect);
                    } else {
                        initializeCount(numberOfChangesOrganizedByFile, file, isDefect);
                    }
                }
            }
        }
        return numberOfChangesOrganizedByFile;
    }
}