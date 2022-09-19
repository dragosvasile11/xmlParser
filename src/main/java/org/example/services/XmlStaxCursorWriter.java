package org.example.services;

import org.example.models.Product;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

/***
 * This class is responsible for writing xml files from Product list.
 * @author dragos
 */
public class XmlStaxCursorWriter {

    /***
     * This method is writing tags and adding Product attributes to the xml file.
     * @param out - 1st parameter is an instance of ByteArrayOutputStream.
     * @param products - 2nd parameter is a list of Product objects.
     * @throws XMLStreamException- if the XMLStreamWriter is not created.
     */
    public void writeXml(OutputStream out, List<Product> products) throws XMLStreamException, NullPointerException {

        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = output.createXMLStreamWriter(out);

        writer.writeStartDocument("utf-8", "1.0");
        writer.writeStartElement("products");
        for (Product product : products) {
            writer.writeStartElement("product");

            writer.writeStartElement("description");
            writer.writeCharacters(product.getDescription());
            writer.writeEndElement();

            writer.writeStartElement("gtin");
            writer.writeCharacters(product.getGtin());
            writer.writeEndElement();

            writer.writeStartElement("price");
            writer.writeAttribute("currency", "USD");
            writer.writeCharacters(String.valueOf(product.getPrice()));
            writer.writeEndElement();

            writer.writeStartElement("orderid");
            writer.writeCharacters(String.valueOf(product.getOrderId()));
            writer.writeEndElement();

            writer.writeEndElement();
        }

        writer.writeEndDocument();
        writer.flush();
        writer.close();
    }

    /***
     * This method is making the xml to look pretty with indention and spaces.
     * @param xml - is the String representation of the converted byte array.
     * @return This method returns a String, representing the xml content being indented and having spaces.
     * @throws TransformerException - if the Transformer is not created.
     */
    public String prettify(String xml) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

        StreamSource source = new StreamSource(new StringReader(xml));
        StringWriter output = new StringWriter();
        transformer.transform(source, new StreamResult(output));

        return output.toString();
    }
}
