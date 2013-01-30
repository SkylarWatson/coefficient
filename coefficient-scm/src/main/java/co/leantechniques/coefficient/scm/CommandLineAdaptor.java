package co.leantechniques.coefficient.scm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CommandLineAdaptor {
    private ProcessBuilder builder = new ProcessBuilder();
    private Process logProcess;

    public void execute(File workingDirectory, List<String> commandLineArguments, CommandLineListener commandLinelistener) {
        start(workingDirectory, commandLineArguments);
        processCommand(commandLinelistener);
        end();
    }

    void start(File workingDirectory, List<String> commandLineArguments) {
        try {
            builder.directory(workingDirectory);
            builder.command(commandLineArguments);
            logProcess = builder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void processCommand(CommandLineListener listener) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(logProcess.getInputStream()));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                listener.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void end() {
        try {
            logProcess.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
