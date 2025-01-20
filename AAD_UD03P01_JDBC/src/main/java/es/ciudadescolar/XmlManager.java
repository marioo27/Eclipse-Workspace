package es.ciudadescolar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(XmlManager.class);

	public static List<Pelicula> procesarXml(File ficheroXml, File ficheroXsd) {

		List<Pelicula> listaPeliculas = new ArrayList<Pelicula>();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setValidating(false);
		dbf.setNamespaceAware(true);

		// File ficheroXsd = new File("peliculas.xsd");
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		Schema schema = null;

		dbf.setIgnoringElementContentWhitespace(true);

		try {
			schema = sf.newSchema(ficheroXsd);

			dbf.setSchema(schema);

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document documento = db.parse(ficheroXml);

			Element raiz = documento.getDocumentElement();

			LOGGER.debug("Se dispone de " + raiz.getChildNodes().getLength() + " nodos");

			NodeList listaNodosPeliculas = raiz.getChildNodes();
			for (int i = 0; i < listaNodosPeliculas.getLength(); i++) {

				Node nodoPelicula = listaNodosPeliculas.item(i);

				if (nodoPelicula.getNodeType() == Node.ELEMENT_NODE) {
					Element elementoPelicula = (Element) nodoPelicula;
					Node nodoNombrePelicula = elementoPelicula.getFirstChild();
					Node nodoAnio = elementoPelicula.getNextSibling();
					NodeList listaNodosActores = (NodeList) elementoPelicula.getLastChild();

					listaNodosActores = raiz.getChildNodes();

					for (int x = 0; x < listaNodosActores.getLength(); x++) {
						List<Actor> listaActores = new ArrayList<Actor>();

						Node nodoActor = listaNodosActores.item(x);

						if (nodoActor.getNodeType() == Node.ELEMENT_NODE) {
							Element elementoActor = (Element) nodoActor;
							Node nodoProtagonista = elementoActor.getFirstChild();
							Node nodoNombreActor = elementoActor.getNextSibling();
							Node nodoApellido = elementoActor.getLastChild();

							boolean protagonista = false;

							if (nodoProtagonista.getTextContent().equals("true"))
								protagonista = true;

							Actor a1 = new Actor(protagonista, nodoNombreActor.getTextContent(),
									nodoApellido.getTextContent());

							listaActores.add(a1);

							Pelicula p1 = new Pelicula(nodoNombrePelicula.getTextContent(),
									Integer.parseInt(nodoAnio.getTextContent()), listaActores);
							
							listaPeliculas.add(p1);
						}

					}
				}
			}

		} catch (SAXException | ParserConfigurationException | IOException e) {
			LOGGER.error("Error al parsear el XML [" + e.getMessage() + "]");
		}

		return listaPeliculas;

	}
}
