package co.leantechniques.coefficient.scm.mercurial;

import co.leantechniques.coefficient.core.CommandLineAdaptor;
import co.leantechniques.coefficient.core.CommandLineListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

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

        Mockito.verify(mockHgCommandLineBuilder).setRangeLimitInDays(90);
        Mockito.verify(mockHgCommandLineBuilder).getCommandLineArguments();
        Mockito.verify(mockCommandLineAdapter).execute(Matchers.eq(workingDirectory), Matchers.anyListOf(String.class), Matchers.any(CommandLineListener.class));
    }
}
