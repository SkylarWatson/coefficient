package co.leantechniques.coefficient.core;

public class Environment {
    public static String getLineSeparator() {
        System.out.println("System.getProperty(\"line.separator\") = " + System.getProperty("line.separator"));
        return System.getProperty("line.separator");
    }

    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }
}
