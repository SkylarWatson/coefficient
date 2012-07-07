package co.leantechniques.coefficient.heatmap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CommandLineAdaptor {

    private ProcessBuilder builder = new ProcessBuilder();
    private Process hgLogProcess;

    public void execute(List<String> commandLineArguments, CommandLineListener commandLinelistener){
        start(commandLineArguments);
        processCommand(commandLinelistener);
        end();
    }

    void start(List<String> commandLineArguments){
        try {
            builder.command(commandLineArguments);
            hgLogProcess = builder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void processCommand(CommandLineListener listener) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(hgLogProcess.getInputStream()));
        String line;
        try {
            while ((line=reader.readLine())!=null) {
                listener.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void end(){
        try {
            hgLogProcess.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
