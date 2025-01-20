package examen1Ev;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FileManager {
	private static final Logger log = LoggerFactory.getLogger(FileManager.class);

	/**
	 * Metodo que parsea un fuchero xsd
	 * 
	 * @param fichero Fichero a parsear
	 * @return
	 */
	public static boolean parsearFichero(File fichero) {

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document documento = db.newDocument();

			db.parse(fichero);

			dbf.setValidating(true);
			dbf.setIgnoringComments(true);

			Element raiz = documento.getDocumentElement();
			NodeList listaNodos = raiz.getChildNodes();

			for (int i = 0; i < listaNodos.getLength(); i++) {
				Node nodo = listaNodos.item(i);
				raiz.appendChild(nodo);
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			log.error("Error" + e.getMessage());
			return false;
		}

		return true;

	}

	/**
	 * Metodo que genera un fichero de salida de tipo Json
	 * @param ficheroSalida
	 */
	public static void generarJson(File ficheroSalida) {

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document documento = db.newDocument();

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			DOMSource ds = new DOMSource(documento);
			StreamResult sr = new StreamResult(new FileWriter(ficheroSalida));
			
			t.setOutputProperty(OutputKeys.METHOD, "xml");
			t.setOutputProperty(OutputKeys.VERSION, "1.0");
			t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(ds, sr);

		} catch (TransformerException | ParserConfigurationException | IOException e) {
			log.error("Error" + e.getMessage());
		}
	}

}
