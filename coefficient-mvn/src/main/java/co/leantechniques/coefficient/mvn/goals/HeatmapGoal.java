package co.leantechniques.coefficient.mvn.goals;

import co.leantechniques.coefficient.heatmap.CodeRepository;
import co.leantechniques.coefficient.heatmap.CodeRepositoryFactory;
import co.leantechniques.coefficient.heatmap.Heatmap;
import co.leantechniques.coefficient.heatmap.WorkingDirectory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Generates a heatmap
 * > mvn coefficient-mvn:heatmap
 *
 * @goal heatmap
 * @parameter expression="${project.build.directory}"
 */

public class HeatmapGoal extends AbstractMojo {
    /**
     * This is the file that the Heatmap will generate.
     *
     * @parameter expression="${project.build.directory}/coefficient/heatmap.html"
     */
    private String outputFile;

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

    /**
     * Number of past days to report on. The default is 90 days.
     *
     * @parameter default-value="90"
     */
    private int rangeLimitInDays = 90;

    private CodeRepositoryFactory factory = new CodeRepositoryFactory();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Generating heatmap in " + outputFile);

        try {
            CodeRepository hg = factory.build(new WorkingDirectory(scmRoot, scmAdapter), rangeLimitInDays);
            Heatmap heatmap = new Heatmap(hg, new FileWriter(outputFile()), "DE\\d+");
            heatmap.generate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File outputFile() {
        File file = new File(outputFile);
        file.getParentFile().mkdirs();
        return file;
    }

}
