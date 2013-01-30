package co.leantechniques.coefficeint.testratio;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LogParserTest {

    public static final String DELIMITER = "\n--\n";

    String text = "my-project/src/com/example/persistence/dao/impl/OrderInvoiceDAOImpl.java" +
            DELIMITER +
            "my-project/src/config/ApplicationCert.properties my-project/src/config/ApplicationDevl.properties my-project/src/config/ApplicationIT.properties my-project/src/config/ApplicationJUnit.properties my-project/src/config/ApplicationLocal.properties my-project/src/config/ApplicationProd.properties my-project/src/config/ApplicationQA.properties my-project/src/config/ApplicationStage.properties" +
            DELIMITER +
            "my-project/src/com/example/service/impl/WarrantyServiceImpl.java" +
            DELIMITER +
            "my-project/src/com/example/service/helper/WarrantyServiceHelper.java my-project/src/com/example/service/helper/TestWarrantyServiceHelper.java" +
            DELIMITER +
            "ApplicationConfigWEB/src/com/example/persistence/dao/mapper/MasterPriceWithCountryAndPackageRowMapperTest.java ApplicationConfigWEB/src/com/example/persistence/dao/mapper/MasterPriceWithCountryAndPackageRowMapper.java ApplicationConfigWEB/src/com/example/persistence/dao/mapper/ProductPackageRowMapper.java ApplicationConfigWEB/src/com/example/persistence/dao/mapper/ProductPackageWithDurationRowMapper.java my-project/src/com/example/ams/ss/test/ProductPackage.java my-project/src/com/example/persistence/dao/impl/ProductDAOImpl.java my-project/src/com/example/persistence/dao/impl/ProductPackageDAOImpl.java my-project/src/com/example/persistence/dao/impl/RefundDAOImpl.java my-project/src/com/example/u90/reusable/service/payment/brokers/PricingBroker.java my-project/src/com/example/pf/Brokers/BaseBroker.java my-project/src/com/example/pf/Hardware/HardwareSubscriptionBroker.properties" +
            DELIMITER;

    private List<LogEntry> entries;

    @Before
    public void setUp() throws Exception {
        LogParser parser = new LogParser(DELIMITER, "java");
        entries = parser.execute(text);
    }

    @Test
    public void buildsLogEntriesFromLogOutput() {
        assertNotNull(entries);
    }

    @Test
    public void willHaveOneEntryPerCommit() {
        assertEquals(5, entries.size());
    }

    @Test
    public void determinesNumberOfFilesInGivenCommit() {
        assertEquals(1, entries.get(0).getNumberOfFilesChanged());
        assertEquals(8, entries.get(1).getNumberOfFilesChanged());
        assertEquals(11, entries.get(4).getNumberOfFilesChanged());
    }

    @Test
    public void determinesNumberOfSourceFilesInGivenCommit() {
        assertEquals(1, entries.get(0).getNumberOfSourceFilesChanged());
        assertEquals(0, entries.get(1).getNumberOfSourceFilesChanged());
        assertEquals(10, entries.get(4).getNumberOfSourceFilesChanged());
    }

    @Test
    public void determinesNumberOfUnitTestsInGivenCommitBasedOnFileName() {
        assertEquals(0, entries.get(0).getNumberOfUnitTestFilesChanged());
        assertEquals(1, entries.get(3).getNumberOfUnitTestFilesChanged());
        assertEquals(1, entries.get(4).getNumberOfUnitTestFilesChanged());
    }
}
