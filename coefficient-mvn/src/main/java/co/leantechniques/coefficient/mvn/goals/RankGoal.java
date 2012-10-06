package co.leantechniques.coefficient.mvn.goals;

import co.leantechniques.coefficient.heatmap.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Generates a report of the top authors of commits with tests
 * > mvn coefficient-mvn:rank
 *
 * @goal rank
 * @parameter expression="${project.build.directory}"
 */

public class RankGoal extends AbstractMojo {

    private CodeRepositoryFactory factory = new CodeRepositoryFactory();

    /**
     * At this time, the plugin is expected to be configured on the POM located at
     * the root of the repository.
     *
     * @parameter expression="${basedir}"
     */
    private String scmRoot;

    /**
     * This is the SCM adapter to use (Mercurial, Git, etc.)
     * For a list of valid SCM systems, please see CodeRepositoryFactoryTest.java
     *
     * @parameter expression="hg"
     */
    private String scmAdapter;


    private ChangesetAnalyzer changesetAnalyzer;


    /**
     * Number of past days to report on. The default is 90 days.
     *
     * @parameter default-value="90"
     */
    private int rangeLimitInDays = 90;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        AuthorStatisticSet authorStatisticSet = getChangesetAnalyzer().getAuthorStatistics();
        getLog().info(String.format("%.2f%% of the %d commits contain test files", authorStatisticSet.getPercentageOfTestedCommits() * 100, authorStatisticSet.getTotalCommits()));


        for (AuthorStatistic commits : authorStatisticSet.toList()) {
            getLog().info(String.format("%.0f%%\t%s\t\t\t(%d of %d commits)",
                    commits.getPercentageOfTestedCommits() * 100,
                    commits.getAuthor(),
                    commits.getCountOfTestedCommits(),
                    commits.getCountOfCommits()));
        }
    }

    private ChangesetAnalyzer getChangesetAnalyzer() {
        if (changesetAnalyzer == null)
            changesetAnalyzer = new ChangesetAnalyzer(factory.build(new WorkingDirectory(scmRoot, scmAdapter), rangeLimitInDays),  "DE\\d+");
        return changesetAnalyzer;
    }
}
