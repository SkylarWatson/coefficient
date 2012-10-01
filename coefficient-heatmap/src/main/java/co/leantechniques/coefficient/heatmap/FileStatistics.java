package co.leantechniques.coefficient.heatmap;

class FileStatistics {
    private int changes;
    private int defects;

    public FileStatistics(int initialNumberOfChanges, int initialNumberOfDefects) {
        changes = initialNumberOfChanges;
        defects = initialNumberOfDefects;
    }

    public double defectRatio() {
        return ((double) getTotalDefects()) / getTotalChanges();
    }

    public int getTotalChanges() {
        return changes;
    }

    public int getTotalDefects() {
        return defects;
    }

    public void markAsDefect() {
        defects++;
    }

    public void recordChange() {
        changes++;
    }
}
