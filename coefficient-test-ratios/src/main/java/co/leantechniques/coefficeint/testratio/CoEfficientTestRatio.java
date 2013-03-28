package co.leantechniques.coefficeint.testratio;

import co.leantechniques.coefficient.scm.CodeRepositoryFactory;
import co.leantechniques.coefficient.scm.WorkingDirectory;

public class CoEfficientTestRatio implements TestRatioListener {
    public static void main(String[] args) {
        CoEfficientTestRatio coEfficientTestRatio = new CoEfficientTestRatio();
        CodeRepositoryFactory codeRepositoryFactory = new CodeRepositoryFactory();

        TestRatio ratio = new TestRatio();
        int pastDaysLimit = 365;
        System.out.println("args[0] = " + args[0]);
        ratio.setCodeRepository(codeRepositoryFactory.build(new WorkingDirectory("."), pastDaysLimit));
        ratio.setTestRatioListener(coEfficientTestRatio);

        ratio.calculate();
    }

    @Override
    public void testRatioCalculated(int percentOfCommitsWithTests) {
        System.out.println(percentOfCommitsWithTests);
    }
}
