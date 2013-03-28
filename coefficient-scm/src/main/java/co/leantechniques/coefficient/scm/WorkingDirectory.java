package co.leantechniques.coefficient.scm;

import co.leantechniques.coefficient.core.Environment;

import java.io.File;

public class WorkingDirectory {
    private String path;
    private String repoDirectoryName;

    @Deprecated
    public WorkingDirectory(String path, String repoDirectoryName) {
        this.path = path;
        this.repoDirectoryName = repoDirectoryName;
    }

    public WorkingDirectory(String path) {
        this.path = path;
        this.repoDirectoryName = null;
    }

    public String getRepoDirectoryName() {
        return repoDirectoryName;
    }

    public File getPath() {
        return new File(path);
    }

    public File getSubdirectory(String directoryName) {
        return new File(path + Environment.getFileSeparator() + directoryName);
    }

    public boolean directoryExists(String directoryName) {
        return getSubdirectory(directoryName).exists();
    }

    public String toString() {
        return path;
    }
}
