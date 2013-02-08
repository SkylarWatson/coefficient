package co.leantechniques.coefficeint.testratio;

import co.leantechniques.coefficient.scm.CodeRepository;
import co.leantechniques.coefficient.scm.Commit;

import java.util.Set;

public class TestRatio {
    private CodeRepository codeRepository;
    private TestRatioListener testRatioListener;

    public void calculate() {
        int percentTested = 0;

        Set<Commit> commits = codeRepository.getCommits();
        for(Commit c : commits) {
            percentTested += c.getPercentFilesWIthTests();
        }

        testRatioListener.testRatioCalculated(percentTested / commits.size());
    }

    public void setCodeRepository(co.leantechniques.coefficient.scm.CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public void setTestRatioListener(TestRatioListener testRatioListener) {
        this.testRatioListener = testRatioListener;
    }
}
