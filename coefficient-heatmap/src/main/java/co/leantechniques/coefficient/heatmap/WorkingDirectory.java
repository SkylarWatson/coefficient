package co.leantechniques.coefficient.heatmap;

import java.io.File;

public class WorkingDirectory {
    private String path;
    private String repoDirectoryName;

    public WorkingDirectory(String path, String repoDirectoryName) {
        this.path = path;
        this.repoDirectoryName = repoDirectoryName;
    }

    public String getRepoDirectoryName() {
        return repoDirectoryName;
    }

    public File getPath() {
        return new File(path);
    }
}
