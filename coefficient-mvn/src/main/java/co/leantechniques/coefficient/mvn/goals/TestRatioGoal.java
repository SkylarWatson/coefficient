package co.leantechniques.coefficient.mvn.goals;

import co.leantechniques.coefficeint.testratio.TestRatio;
import co.leantechniques.coefficeint.testratio.TestRatioListener;
import co.leantechniques.coefficient.scm.CodeRepositoryFactory;
import co.leantechniques.coefficient.scm.WorkingDirectory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Generates a heatmap
 * > mvn coefficient-mvn:test-ratio
 *
 * @goal test-ratio
 * @parameter expression="${project.build.directory}"
 */

public class TestRatioGoal extends AbstractMojo implements TestRatioListener {
    /**
     * Number of past days to report on. The default is 90.
     *
     * @parameter default-value="90"
     */
    private int rangeLimitInDays = 90;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        TestRatio testRatio = new TestRatio();
        testRatio.setCodeRepository(new CodeRepositoryFactory().build(new WorkingDirectory("."), rangeLimitInDays));
        testRatio.setTestRatioListener(this);
        testRatio.calculate();
    }

    @Override
    public void testRatioCalculated(int percentOfCommitsWithTests) {
        getLog().info("Over the last " + rangeLimitInDays + " days, " + percentOfCommitsWithTests + "% of the production source files committed contain tests");
    }

    @Override
    public void noProductionSourceCommitted() {
        getLog().info("No production code was found in commit history. Nothing to calculate.");
    }
}