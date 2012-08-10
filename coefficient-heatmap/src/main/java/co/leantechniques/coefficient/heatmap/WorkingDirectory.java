package co.leantechniques.coefficient.heatmap;

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

    public String getPath() {
        return path;
    }
}
