package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Test;

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
    public void startUsesTheCommandLineArguments(){
        doNothing().when(commandLineAdaptor).start(anyList());
        doNothing().when(commandLineAdaptor).processCommand(any(CommandLineListener.class));
        doNothing().when(commandLineAdaptor).end();

        commandLineAdaptor.execute(expectedCommandLineArguments, null);

        verify(commandLineAdaptor).start(expectedCommandLineArguments);
    }
}
