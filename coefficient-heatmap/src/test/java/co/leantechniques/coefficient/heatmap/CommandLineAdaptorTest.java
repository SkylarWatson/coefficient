package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.*;

public class CommandLineAdaptorTest {

    private CommandLineAdaptor commandLineAdaptor;
    private List<String> expectedCommandLineArguments;

    @Before
    public void setUp() throws Exception {
        commandLineAdaptor = spy(new CommandLineAdaptor());
        expectedCommandLineArguments = new ArrayList<String>();
    }

    @Test
    public void startUsesTheCommandLineArguments() {
        File workingDirectory = new File("working");

        doNothing().when(commandLineAdaptor).start(eq(workingDirectory), anyList());
        doNothing().when(commandLineAdaptor).processCommand(any(CommandLineListener.class));
        doNothing().when(commandLineAdaptor).end();

        commandLineAdaptor.execute(workingDirectory, expectedCommandLineArguments, null);

        verify(commandLineAdaptor).start(workingDirectory, expectedCommandLineArguments);
    }
}
