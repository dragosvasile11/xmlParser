import org.example.services.DirectoryWatcher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectoryWatcherTest {
    final DirectoryWatcher directoryWatcher = new DirectoryWatcher();

    @Test
    void testValidateInputWithCorrectFileName() {
        Boolean actualFileName = directoryWatcher.validateInput("orders23.xml");
        Boolean expectedFileName = true;
        assertEquals(expectedFileName, actualFileName);
    }

    @Test
    void testValidateInputWithIncorrectFileName1() {
        Boolean actualFileName = directoryWatcher.validateInput("ordersss.xml");
        Boolean expectedFileName = false;
        assertEquals(expectedFileName, actualFileName);
    }

    @Test
    void testValidateInputWithIncorrectFileName2() {
        Boolean actualFileName = directoryWatcher.validateInput("Orders42.xml");
        Boolean expectedFileName = false;
        assertEquals(expectedFileName, actualFileName);
    }

}
