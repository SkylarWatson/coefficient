package co.leantechniques.coefficient.heatmap;

import java.text.SimpleDateFormat;
import java.util.*;

public class CommandLineBuilder {
    private int previousDaysFilter;

    public List<String> getCommandLineArguments() {
        List<String> commandLineArguments = new ArrayList<String>(Arrays.asList("hg", "log", "--no-merge", "--template", "{author|user}||{desc}||{files}\\n"));
        addDateFilter(commandLineArguments);
        // TODO: Add Logging?
//        System.out.println("Commandline: " + Arrays.toString(commandLineArguments.toArray()));
        return commandLineArguments;
    }

    private void addDateFilter(List<String> commandLineArguments) {
        if(previousDaysFilter > 0){
            commandLineArguments.add("--date");
            commandLineArguments.add(calculateDateRangeString());
        }
    }

    private String calculateDateRangeString() {
        Date endDate = SystemClock.getCurrentDate();
        Date startDate = SystemClock.addDays(-previousDaysFilter);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(startDate) + " to " + dateFormat.format(endDate);
    }

    public void setRangeLimitInDays(int previousDaysFilter) {
        this.previousDaysFilter = previousDaysFilter;
    }
}
