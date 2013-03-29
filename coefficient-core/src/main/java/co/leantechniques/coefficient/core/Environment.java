package co.leantechniques.coefficient.core;

public class Environment {
    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }

    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }
}
