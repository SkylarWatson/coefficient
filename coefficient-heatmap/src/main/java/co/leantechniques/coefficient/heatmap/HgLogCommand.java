package co.leantechniques.coefficient.heatmap;

public class HgLogCommand implements LogCommand {

    private CommandLineAdaptor commandLineAdapter = new CommandLineAdaptor();
    private CommandLineBuilder commandLineBuilder = new CommandLineBuilder();

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

    public void setCommandLineBuilder(CommandLineBuilder commandLineBuilder) {
        this.commandLineBuilder = commandLineBuilder;
    }

    public void setPastDaysLimit(int pastDaysLimit) {
        this.commandLineBuilder.setRangeLimitInDays(pastDaysLimit);
    }
}
