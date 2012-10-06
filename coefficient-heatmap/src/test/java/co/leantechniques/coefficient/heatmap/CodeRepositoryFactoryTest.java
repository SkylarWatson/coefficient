package co.leantechniques.coefficient.heatmap;

import co.leantechniques.coefficient.heatmap.git.GitCodeRepository;
import co.leantechniques.coefficient.heatmap.mecurial.MercurialCodeRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CodeRepositoryFactoryTest {
    private CodeRepositoryFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new CodeRepositoryFactory();
    }

    @Test
    public void determineBasedOnRepositoryLocation() {
        assertIsType(MercurialCodeRepository.class, factory.build(workingDirectoryFor("hg"), 90));
        assertIsType(GitCodeRepository.class, factory.build(workingDirectoryFor("git"), 90));
    }

    private void assertIsType(Class expectedRepositoryType, CodeRepository actualRepository) {
        assertTrue("Expected repo of type: " + expectedRepositoryType + ", but got " + actualRepository.getClass(),
                expectedRepositoryType.isInstance(actualRepository));
    }

    private WorkingDirectory workingDirectoryFor(String repoType) {
        WorkingDirectory hgWorking = mock(WorkingDirectory.class);
        when(hgWorking.getRepoDirectoryName()).thenReturn(repoType);
        return hgWorking;
    }
}

