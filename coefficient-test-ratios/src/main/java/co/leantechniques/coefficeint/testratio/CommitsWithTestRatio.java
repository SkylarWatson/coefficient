package co.leantechniques.coefficeint.testratio;

import co.leantechniques.coefficient.scm.CodeRepository;
import co.leantechniques.coefficient.scm.Commit;

import java.util.Set;

public class CommitsWithTestRatio {
    private CodeRepository codeRepository;
    private TestRatioListener testRatioListener;

    public void process() {
        Set<Commit> commits = codeRepository.getCommits();
        for(Commit c : commits) {

        }
        testRatioListener.testRatioCalculated(50);
    }

    public void setCodeRepository(co.leantechniques.coefficient.scm.CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public void setTestRatioListener(TestRatioListener testRatioListener) {
        this.testRatioListener = testRatioListener;
    }
}
