package co.leantechniques.coefficeint.testratio;

import co.leantechniques.coefficient.scm.CodeRepository;
import co.leantechniques.coefficient.scm.Commit;
import org.junit.Test;

import java.util.HashSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestRatioTest {
    @Test
    public void doesStuff() {
        CodeRepository repo = mock(CodeRepository.class);
        TestRatioListener listener = mock(TestRatioListener.class);

        TestRatio ratio = new TestRatio();
        ratio.setCodeRepository(repo);
        ratio.setTestRatioListener(listener);

        HashSet<Commit> commits = new HashSet<Commit>();
        commits.add(new Commit("Brandon", "DE1234", "com/example/File1.java", "com/example/File1Test.java"));
        commits.add(new Commit("Brandon", "US1234", "com/example/File1.java", "com/example/File2.java"));

        when(repo.getCommits()).thenReturn(commits);

        ratio.calculate();

        verify(listener).testRatioCalculated(50);
    }

}
