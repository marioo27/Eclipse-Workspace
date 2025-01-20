package UD02A02_XML_DOM;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XmlManager {

    public static Collection<Item> parsearXml(File fichero) {
        Collection<Item> listaItems = new ArrayList<>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File("cve_1.0.xsd"));
            dbf.setSchema(schema);

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document documento = db.parse(fichero);

            NodeList nodos = documento.getElementsByTagName("item");
            for (int i = 0; i < nodos.getLength(); i++) {
                Element elementoItem = (Element) nodos.item(i);

                String name = elementoItem.getAttribute("name");
                String seq = elementoItem.getAttribute("seq");
                String type = elementoItem.getAttribute("type");
                String status = elementoItem.getAttribute("status");
                String desc = elementoItem.getTextContent().trim();

                listaItems.add(new Item(name, seq, type, status, desc));
            }
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return listaItems;
    }

    public static void generarXml(Collection<Item> listaItems, File ficheroSalida) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document documento = db.newDocument();

            Element raiz = documento.createElement("items");
            documento.appendChild(raiz);

            for (Item i : listaItems) {
                Element elementoItem = documento.createElement("item");
                elementoItem.setAttribute("name", i.getName());
                elementoItem.setAttribute("seq", i.getSeq());
                elementoItem.setAttribute("type", i.getType());
                elementoItem.setAttribute("status", i.getStatus());
                elementoItem.setTextContent(i.getDesc());
                raiz.appendChild(elementoItem);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            t.transform(new DOMSource(documento), new StreamResult(new FileWriter(ficheroSalida)));
        } catch (ParserConfigurationException | TransformerException | IOException e) {
            e.printStackTrace();
        }
    }
}
