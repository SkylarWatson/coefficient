package co.leantechniques.coefficeint.testratio;

import co.leantechniques.coefficient.scm.CodeRepository;
import co.leantechniques.coefficient.scm.Commit;
import org.junit.Test;

import java.util.HashSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CommitsWithTestRatioTest {
    @Test
    public void doesStuff() {
        CodeRepository repo = mock(CodeRepository.class);
        TestRatioListener listener = mock(TestRatioListener.class);

        CommitsWithTestRatio ratio = new CommitsWithTestRatio();
        ratio.setCodeRepository(repo);
        ratio.setTestRatioListener(listener);

        HashSet<Commit> commits = new HashSet<Commit>();
        commits.add(new Commit("Brandon", "DE1234", "File1.java", "File1Test.java"));
        commits.add(new Commit("Brandon", "US1234", "File1.java", "File2.java"));

        when(repo.getCommits()).thenReturn(commits);

        ratio.process();

        verify(listener).testRatioCalculated(50);
    }

}
