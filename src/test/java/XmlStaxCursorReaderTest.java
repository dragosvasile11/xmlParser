import org.example.models.Product;
import org.example.services.XmlStaxCursorReader;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class XmlStaxCursorReaderTest {

    final XmlStaxCursorReader xmlReader = new XmlStaxCursorReader();

    @Test
    void sortProductsByTimestampAndPriceTest() {
        HashMap<String, List<Product>> actualProductsBySupplier = new HashMap<>();
        HashMap<String, List<Product>> expectedProductsBySupplier = new HashMap<>();

        actualProductsBySupplier.put("Sony", new ArrayList<>());
        expectedProductsBySupplier.put("Sony", new ArrayList<>());

        Product product1 = new Product();
        product1.setLocalDateTime(LocalDateTime.parse("2012-07-12T15:29:33.000"));
        product1.setPrice(2999.99);

        Product product2 = new Product();
        product2.setLocalDateTime(LocalDateTime.parse("2012-07-13T16:29:33.000"));
        product2.setPrice(1999.99);

        Product product3 = new Product();
        product3.setLocalDateTime(LocalDateTime.parse("2012-07-13T16:29:33.000"));
        product3.setPrice(99.99);

        actualProductsBySupplier.get("Sony").add(product1);
        actualProductsBySupplier.get("Sony").add(product2);
        actualProductsBySupplier.get("Sony").add(product3);

        expectedProductsBySupplier.get("Sony").add(product2);
        expectedProductsBySupplier.get("Sony").add(product3);
        expectedProductsBySupplier.get("Sony").add(product1);

        assertEquals(expectedProductsBySupplier, xmlReader.sortProductsByTimestampAndPrice(actualProductsBySupplier));
    }

    @Test
    void checkIfSortingMethodThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> xmlReader.sortProductsByTimestampAndPrice(null));
    }

    @Test
    void checkIfReadXmlThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> xmlReader.readXml(null));
    }
}
