package co.leantechniques.coefficient.heatmap.git;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GitCommitBuilder {
    private String author;
    private List<String> message = new ArrayList<String>();
    private List<String> files = new ArrayList<String>();

    public GitCommitBuilder author(String authorName) {
        this.author = authorName;
        return this;
    }

    public GitCommitBuilder message(String... message) {
        this.message.addAll(Arrays.asList(message));
        return this;
    }

    public GitCommitBuilder file(String... file) {
        this.files.addAll(Arrays.asList(file));
        return this;
    }

    public List<String> toLines() {
        List<String> lines = new ArrayList<String>();
        lines.add("commit 11111111111");
        lines.add("Author: " + author + " <joe.blow@blah.com>");
        lines.add("Date:  Sun Aug 5 17:31:55 2012 -0500");
        lines.add("  ");
        lines.addAll(message);
        lines.add("  ");
        lines.addAll(files);
        return lines;
    }
}
