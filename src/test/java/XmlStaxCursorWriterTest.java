import org.example.models.Product;
import org.example.services.XmlStaxCursorWriter;
import org.junit.jupiter.api.Test;

import javax.xml.transform.TransformerException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class XmlStaxCursorWriterTest {

    final XmlStaxCursorWriter writer = new XmlStaxCursorWriter();

    @Test
    void checkIfPrettifyIsThrowingTransformerException() {
        assertThrows(TransformerException.class, () -> writer.prettify(""));
    }

    @Test
    void checkIfWriteXmlIsThrowingNullPointerException() {
        List<Product> products = new ArrayList<>();
        assertThrows(NullPointerException.class, () -> writer.writeXml(null, products));
    }
}
