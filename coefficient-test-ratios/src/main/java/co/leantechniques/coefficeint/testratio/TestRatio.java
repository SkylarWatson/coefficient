package co.leantechniques.coefficeint.testratio;

import co.leantechniques.coefficient.scm.CodeRepository;
import co.leantechniques.coefficient.scm.Commit;

import java.util.HashSet;
import java.util.Set;

public class TestRatio {
    private CodeRepository codeRepository;
    private TestRatioListener testRatioListener;

    public void calculate() {

        Set<Commit> productionCommits = new CommitFilter(codeRepository.getCommits()).production();

        int totalForAllCommits = calculateTestRatio(productionCommits);

        if (productionCommits.isEmpty())
            testRatioListener.noProductionSourceCommitted();
        else {
            int finalRatio = totalForAllCommits / productionCommits.size();
            testRatioListener.testRatioCalculated(finalRatio);
        }
    }

    private int calculateTestRatio(Set<Commit> productionCommits) {
        int percentTested = 0;

        for(Commit c : productionCommits) {
            percentTested += ratioFor(c);
        }
        return percentTested;
    }

    private int ratioFor(Commit c) {
        double ratio = calculateTestRatio(c.getTestSourceFiles(), c.getProductionSourceFiles());
        return toWholeNumber(ratio);
    }

    private double calculateTestRatio(Set<String> testSources, Set<String> productionSources) {
        return (double) testSources.size() / (double) productionSources.size();
    }

    private int toWholeNumber(double ratio) {
        return (int) (ratio * 100);
    }

    public void setCodeRepository(co.leantechniques.coefficient.scm.CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public void setTestRatioListener(TestRatioListener testRatioListener) {
        this.testRatioListener = testRatioListener;
    }

    private class CommitFilter {
        private Set<Commit> commits;

        public CommitFilter(Set<Commit> commits) {
            this.commits = commits;
        }

        public Set<Commit> production() {
            Set<Commit> commits = new HashSet<Commit>();
            for (Commit c : this.commits) {
                if (c.containsProductionCode()) {
                    commits.add(c);
                }
            }
            return commits;
        }
    }
}
