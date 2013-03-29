package co.leantechniques.coefficeint.testratio;

public interface TestRatioListener {
    void testRatioCalculated(int percentOfCommitsWithTests);

    void nothingToTest();
}
