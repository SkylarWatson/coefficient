package co.leantechniques.coefficient.scm.mercurial;

import co.leantechniques.coefficient.scm.CommandLineAdaptor;
import co.leantechniques.coefficient.scm.CommandLineListener;
import co.leantechniques.coefficient.core.Environment;

import java.io.File;

public class HgLogCommand {
    private CommandLineAdaptor commandLineAdapter = new CommandLineAdaptor();
    private HgCommandLineBuilder commandLineBuilder = new HgCommandLineBuilder();

    public String execute(File workingDirectory, int rangeLimitInDays) {
        final StringBuilder commitData = new StringBuilder();
        commandLineBuilder.setRangeLimitInDays(rangeLimitInDays);
        commandLineAdapter.execute(workingDirectory, commandLineBuilder.getCommandLineArguments(), new CommandLineListener() {
            @Override
            public void add(String line) {
                commitData.append(line);
                commitData.append(Environment.getLineSeparator());
            }
        });
        return commitData.toString();
    }
}
