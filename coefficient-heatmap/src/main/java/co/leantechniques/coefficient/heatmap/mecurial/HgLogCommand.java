package co.leantechniques.coefficient.heatmap.mecurial;

import co.leantechniques.coefficient.heatmap.CommandLineAdaptor;
import co.leantechniques.coefficient.heatmap.CommandLineListener;
import co.leantechniques.coefficient.heatmap.Environment;
import co.leantechniques.coefficient.heatmap.LogCommand;

public class HgLogCommand implements LogCommand {

    private CommandLineAdaptor commandLineAdapter = new CommandLineAdaptor();
    private HgCommandLineBuilder commandLineBuilder = new HgCommandLineBuilder();

    public HgLogCommand() {
    }

    @Override
    public String execute(int rangeLimitInDays) {
        final StringBuilder commitData = new StringBuilder();
        commandLineBuilder.setRangeLimitInDays(rangeLimitInDays);
        commandLineAdapter.execute(commandLineBuilder.getCommandLineArguments(), new CommandLineListener() {
            @Override
            public void add(String line) {
                commitData.append(line);
                commitData.append(Environment.getLineSeparator());
            }
        });
//        System.out.println("====================");
//        System.out.print(commitData.toString());
//        System.out.println("====================");

        return commitData.toString();
    }

    public void setCommandLineAdapter(CommandLineAdaptor commandLineAdapter) {
        this.commandLineAdapter = commandLineAdapter;
    }

    public void setCommandLineBuilder(HgCommandLineBuilder commandLineBuilder) {
        this.commandLineBuilder = commandLineBuilder;
    }

    public void setPastDaysLimit(int pastDaysLimit) {
        this.commandLineBuilder.setRangeLimitInDays(pastDaysLimit);
    }
}
