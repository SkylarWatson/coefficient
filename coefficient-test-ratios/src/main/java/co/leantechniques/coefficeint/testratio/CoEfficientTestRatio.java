package co.leantechniques.coefficeint.testratio;

import co.leantechniques.coefficient.scm.CodeRepositoryFactory;
import co.leantechniques.coefficient.scm.WorkingDirectory;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class CoEfficientTestRatio implements TestRatioListener {
    @Parameter(names = {"--limit"}, description = "Number of days to go back through history")
    private int dailyLimit = 365;

    @Parameter(names = {"--bare"}, description = "Output the ratio in a format suitable for piping")
    private boolean bare = false;

    public static void main(String[] args) {
        CodeRepositoryFactory codeRepositoryFactory = new CodeRepositoryFactory();
        CoEfficientTestRatio coEfficientTestRatio = new CoEfficientTestRatio();
        JCommander jc = new JCommander(coEfficientTestRatio);

        try {
            jc.parse(args);

            TestRatio ratio = new TestRatio();
            ratio.setCodeRepository(codeRepositoryFactory.build(new WorkingDirectory("."), coEfficientTestRatio.dailyLimit));
            ratio.setTestRatioListener(coEfficientTestRatio);

            ratio.calculate();
        } catch (ParameterException e) {
            jc.setProgramName("coefficient-test-ratio");
            jc.usage();
        } catch (UnsupportedOperationException e) {
            System.out.println("Unable to determine repository type. Are you in the proper directory?");
        }
    }

    @Override
    public void testRatioCalculated(int percentOfCommitsWithTests) {
        if (bare)
            System.out.println(percentOfCommitsWithTests);
        else
            System.out.println("Over the last " + dailyLimit + " days, " + percentOfCommitsWithTests + "% of the production source files committed contain tests");
    }

    @Override
    public void noProductionSourceCommitted() {
        System.out.println("No production code was found in commit history. Nothing to calculate.");
    }
}
