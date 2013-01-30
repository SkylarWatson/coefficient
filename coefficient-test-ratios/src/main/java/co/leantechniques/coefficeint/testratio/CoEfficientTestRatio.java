package co.leantechniques.coefficeint.testratio;

import co.leantechniques.coefficient.scm.CodeRepositoryFactory;
import co.leantechniques.coefficient.scm.WorkingDirectory;

public class CoEfficientTestRatio implements TestRatioListener {
    public static void main(String[] args) {
        CoEfficientTestRatio coEfficientTestRatio = new CoEfficientTestRatio();

        CommitsWithTestRatio app = new CommitsWithTestRatio();
        CodeRepositoryFactory codeRepositoryFactory = new CodeRepositoryFactory();
        app.setCodeRepository(codeRepositoryFactory.build(new WorkingDirectory(".", "git"), 7));
        app.process();
    }
    @Override
    public void testRatioCalculated(int percentOfCommitsWithTests) {
        System.out.println(percentOfCommitsWithTests);
    }
}
