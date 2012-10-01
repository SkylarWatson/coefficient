package co.leantechniques.coefficient.heatmap;

import java.io.File;
import java.util.*;

public class HtmlRenderer {
    public static final int MINIMUM_SIZE = 6;
    public static final int DIFFERENCE_IN_TAG_SIZE = 3;
    public static final int NUMBER_OF_CLASSIFICATIONS = 10;

    private Map<String, FileStatistics> files;
    private float range;
    private Integer floor;

    public HtmlRenderer(Map<String, FileStatistics> files) {
        this.files = files;
    }

    public String render() {
        if (files.isEmpty()) return renderEmptyReport();
        ArrayList changeCounts = sorted(files.values());
        floor = min(changeCounts);
        range = max(changeCounts) - floor;
        String heatmap = getHtmlHeader();
        for (String file : sorted(files.keySet())) {
            heatmap += tagFor(file);
        }
        return heatmap + getHtmlFooter();
    }

    private String getHtmlHeader() {
        return "<html>" +
                "<head>" +
                "<title>SCM Heatmap</title>" +
                "<style type='text/css'>" +
                "body { font-family: sans-serif; padding: 0; margin: 0; }" +
                "ol { margin: 0; padding: 20px; }" +
                "ol li { display: inline-block; margin: 2px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<ol>";
    }

    private String getHtmlFooter() {
        return "</ol>" +
                "</body>" +
                "</html>";
    }

    private String renderEmptyReport() {
        String heatmap = getHtmlHeader();
        heatmap += "No Results";
        return heatmap + getHtmlFooter();
    }

    private ArrayList sorted(Collection<FileStatistics> values) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (FileStatistics changeData : values) {
            list.add(changeData.getTotalChanges());
        }
        Collections.sort(list);
        return list;
    }

    private Integer max(ArrayList<Integer> presortedValues) {
        return presortedValues.get(presortedValues.size() - 1);
    }

    private Integer min(ArrayList<Integer> presortedValues) {
        return presortedValues.get(0);
    }

    private String tagFor(String file) {
        FileStatistics stats = statisticsFor(file);
        return "<li " + styleOf(stats) +
                " title='" + file + " -> " + "Changes: " + stats.getTotalChanges() + "  Defects: " + stats.getTotalDefects() +
                "'>" + baseNameOf(file) + "</li>";
    }

    private FileStatistics statisticsFor(String file) {
        return files.get(file);
    }

    private String styleOf(FileStatistics statistics) {
        return "style='" +
                "font-size: " + calculatedSizeFor(statistics.getTotalChanges()) + ";" +
                "color: " + calculateColorFor(statistics) + "'";
    }

    private int calculatedSizeFor(int changeCount) {
        return MINIMUM_SIZE + adjustedSize(changeCount);
    }

    private String calculateColorFor(FileStatistics statistics) {
        double percentOfDefects = statistics.defectRatio();
        int red = (int) (percentOfDefects * 255);
        if (red == 0)
            return "rgb(211,211,211)";
        else
            return "rgb(" + red + ",0,0)";
    }

    private int adjustedSize(int changeCount) {
        return amplify(classify(normalized(changeCount)));
    }

    private int amplify(int i) {
        return i * DIFFERENCE_IN_TAG_SIZE;
    }

    private int classify(float changes) {
        return (int) (changes * NUMBER_OF_CLASSIFICATIONS);
    }

    private float normalized(int changeCount) {
        return (changeCount - floor) / range;
    }

    private String baseNameOf(String file) {
        File f = new File(file);
        return f.getName().split("\\.")[0];
    }

    private ArrayList<String> sorted(Set<String> filenames) {
        ArrayList<String> files = new ArrayList<String>(filenames);
        Collections.sort(files);
        return files;
    }
}
