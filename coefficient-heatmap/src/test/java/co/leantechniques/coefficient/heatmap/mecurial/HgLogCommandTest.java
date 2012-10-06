package co.leantechniques.coefficient.heatmap.mecurial;

import co.leantechniques.coefficient.heatmap.CommandLineAdaptor;
import co.leantechniques.coefficient.heatmap.CommandLineListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HgLogCommandTest {
    @Mock
    private CommandLineAdaptor mockCommandLineAdapter;
    @Mock
    private HgCommandLineBuilder mockHgCommandLineBuilder;

    @InjectMocks
    HgLogCommand command = new HgLogCommand();

    @Test
    public void executeCommandLine() {
        File workingDirectory = new File("working");

        command.execute(workingDirectory, 90);

        verify(mockHgCommandLineBuilder).setRangeLimitInDays(90);
        verify(mockHgCommandLineBuilder).getCommandLineArguments();
        verify(mockCommandLineAdapter).execute(eq(workingDirectory), anyListOf(String.class), any(CommandLineListener.class));
    }
}
