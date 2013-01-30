package co.leantechniques.coefficeint.testratio;

public class LogEntry {

    private int totalFilesChanged;
    private int numberOfSourceFilesChanged;
    private int numberOfUnitTestFilesChanged;
    private int testRatio;

    public LogEntry(int totalFilesChanged, int numberOfSourceFiles, int numberOfTestFiles) {
        this.totalFilesChanged = totalFilesChanged;
        this.numberOfSourceFilesChanged = numberOfSourceFiles;
        this.numberOfUnitTestFilesChanged = numberOfTestFiles;
        this.testRatio = percentageOf(this.numberOfUnitTestFilesChanged, this.numberOfSourceFilesChanged);
    }

    private int percentageOf(double testsChanged, double sourceChanged) {
        return (int) (testsChanged / sourceChanged * 100);
    }

    public int getNumberOfFilesChanged() {
        return totalFilesChanged;
    }

    public int getNumberOfSourceFilesChanged() {
        return numberOfSourceFilesChanged;
    }

    public int getNumberOfUnitTestFilesChanged() {
        return numberOfUnitTestFilesChanged;
    }

    public int getTestRatio() {
        return testRatio;
    }
}
