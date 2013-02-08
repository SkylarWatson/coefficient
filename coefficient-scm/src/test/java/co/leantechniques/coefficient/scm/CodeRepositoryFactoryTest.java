package co.leantechniques.coefficient.scm;

import co.leantechniques.coefficient.scm.git.GitCodeRepository;
import co.leantechniques.coefficient.scm.mercurial.MercurialCodeRepository;

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

    @Test
    public void supportsGit() {
        assertIsType(GitCodeRepository.class, factory.build(mockRepository("git"), 0));
    }

    @Test
    public void supportsMercurial() {
        assertIsType(MercurialCodeRepository.class, factory.build(mockRepository("hg"), 0));
    }

    private WorkingDirectory mockRepository(String repositoryType) {
        WorkingDirectory gitRepoDirectory = mock(WorkingDirectory.class);
        when(gitRepoDirectory.directoryExists("." + repositoryType)).thenReturn(true);
        return gitRepoDirectory;
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

