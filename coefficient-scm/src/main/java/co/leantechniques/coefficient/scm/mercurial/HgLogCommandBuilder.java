package co.leantechniques.coefficient.scm.mercurial;

import java.util.Arrays;
import java.util.List;

public class HgLogCommandBuilder {

    private List<String> commandLine = Arrays.asList("hg", "log", "--no-merge", "--template", "{author|user}||{desc}||{files}\\n");

    public String getDate() {
        return null;
    }

    public List<String> getCommandLine() {
        return commandLine;
    }
}
