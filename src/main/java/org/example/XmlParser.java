package org.example;

import org.example.models.Product;
import org.example.services.DirectoryWatcher;
import org.example.services.XmlStaxCursorReader;
import org.example.services.XmlStaxCursorWriter;
import utils.ConfigFileReader;
import utils.ExceptionsMessages;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

/***
 * This class represent the application logic.
 * <p>DirectoryWatcher is implemented to find new received files from the customers orders (in input directory).</p>
 * <p>The XmlStaxCursorReader is reading the xml files.</p>
 * <p>The data is saved as Product objects.</p>
 * <p>The XmlStaxCursorWriter is writing new xml files with data from Product objects, saved in output directory by suppliers.</p>
 */
public class XmlParser {

    /***
     * This method is used to create new objects that are used for the app logic and call their methods.
     * @param args not used.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {

        try {
            ConfigFileReader.readConfigFile();
        } catch (IOException e) {
            throw new RuntimeException(ExceptionsMessages.RUNTIME_EXCEPTION_MESSAGE, e);
        }

        while(true) {

            DirectoryWatcher dw = new DirectoryWatcher();
            String fileName;

            try {
                fileName = dw.watchDirectory();
            } catch (InterruptedException e) {
                throw new RuntimeException(ExceptionsMessages.RUNTIME_EXCEPTION_MESSAGE, e);
            }

            XmlStaxCursorReader xmlReader = new XmlStaxCursorReader();

            HashMap<String, List<Product>> productsBySupplier;

            try {
                productsBySupplier = xmlReader.readXml(fileName);
            } catch (FileNotFoundException | XMLStreamException e) {
                throw new RuntimeException(e);
            }

            XmlStaxCursorWriter xmlWriter = new XmlStaxCursorWriter();

            for (HashMap.Entry<String, List<Product>> supplier : productsBySupplier.entrySet()) {
                try {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();

                    xmlWriter.writeXml(out, supplier.getValue());

                    String xml = out.toString(StandardCharsets.UTF_8);

                    String prettyPrintXML = xmlWriter.prettify(xml);

                    Files.writeString(Paths.get(ConfigFileReader.properties.getProperty("outputPath") +
                                    supplier.getKey() +
                                    fileName.substring(fileName.length() - 6)),
                            prettyPrintXML, StandardCharsets.UTF_8);
                } catch (TransformerException | XMLStreamException | IOException e) {
                    e.printStackTrace();
                }
            }
            productsBySupplier.clear();
        }
    }
}
