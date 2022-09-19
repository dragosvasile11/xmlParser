package org.example.services;

import org.example.models.Product;
import utils.ConfigFileReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/***
 * This class is responsible for reading xml files.
 * <p>Creating Product objects to save the data.</p>
 * <p>Sorting Product objects by attributes (timestamp and price).</p>
 * @author dragos
 */
public class XmlStaxCursorReader {

    /***
     * This method is reading xml file and saving data from it as Product objects.
     * @param fileName - This is a String representation of the file name.
     * @return This method returns a hashmap with keys representing the suppliers and values as an ArrayList of Product objects.
     * @throws FileNotFoundException - if FileReader is not receiving the file to read.
     * @throws XMLStreamException - if the XMLStreamReader is not created.
     */
    public HashMap<String, List<Product>> readXml(String fileName) throws FileNotFoundException, XMLStreamException, NullPointerException {

        HashMap<String, List<Product>> productsBySupplier = new HashMap<>();

        Product product = null;
        String id = "";
        String timeStamp = "";

        File file = new File(ConfigFileReader.properties.getProperty("inputPath") + fileName);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader streamReader = factory.createXMLStreamReader(new FileReader(file));

        while(streamReader.hasNext()) {

            streamReader.next();

            if(isEndElement(streamReader, streamReader.START_ELEMENT)) {
                if(containsTag(streamReader, "order")) {
                    id = streamReader.getAttributeValue(null,"ID");
                    timeStamp = streamReader.getAttributeValue(null,"created");
                }

                if(containsTag(streamReader, "product")) {
                    product = new Product();
                    product.setOrderId(Integer.valueOf(id));
                    product.setLocalDateTime(LocalDateTime.parse(timeStamp));
                }
                if (product != null) {
                    if(containsTag(streamReader, "description")) {
                        product.setDescription(streamReader.getElementText());
                    }

                    if(containsTag(streamReader, "gtin")) {
                        product.setGtin(streamReader.getElementText());
                    }

                    if(containsTag(streamReader, "price")) {
                        product.setPrice(Double.valueOf(streamReader.getElementText()));
                    }

                    if(containsTag(streamReader, "supplier")) {
                        product.setSupplier(streamReader.getElementText());
                    }
                }
            }

            if (isEndElement(streamReader, streamReader.END_ELEMENT) && containsTag(streamReader, "product") && product != null) {
                if (!productsBySupplier.containsKey(product.getSupplier())) {
                    productsBySupplier.put(product.getSupplier(), new ArrayList<>());
                }
                productsBySupplier.get(product.getSupplier()).add(product);
            }
        }
        return sortProductsByTimestampAndPrice(productsBySupplier);
    }

    /***
     * The method is sorting Product objects by LocalDateTime and Price.
     * @param productsBySupplier - it should be a hashmap with String as a key and list of Product objects as a value.
     * @return HashMap<String, List<Product>>. This method returns a hashmap with sorted products.
     */
    public HashMap<String, List<Product>> sortProductsByTimestampAndPrice(HashMap<String, List<Product>> productsBySupplier) {

        for (HashMap.Entry<String, List<Product>> supplier : productsBySupplier.entrySet()) {
            supplier.getValue().sort(Comparator.comparing(Product::getLocalDateTime).thenComparing(Product::getPrice).reversed());
        }
        return productsBySupplier;
    }

    private boolean isEndElement(XMLStreamReader streamReader, int endElementPosition) {
        return streamReader.getEventType() == endElementPosition;
    }

    private boolean containsTag(XMLStreamReader streamReader, String tag) {
        return streamReader.getLocalName().equalsIgnoreCase(tag);
    }
}
