package es.ciudadescolar.xpath;

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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlManager {

	private static Document documento;
	private static final Logger LOGGER = LoggerFactory.getLogger(XmlManager.class);

	public static boolean parsearXmlDomXsd(File xml, File xsd) {

		DocumentBuilderFactory dbf = null;
		DocumentBuilder db = null;

		SchemaFactory sf = null;
		Schema schema = null;

		LOGGER.trace("Preparando el document builder factory: espacios en blanco, validacion no DTD, namespace por defecto...");
		
		dbf = DocumentBuilderFactory.newInstance();

		dbf.setIgnoringElementContentWhitespace(true);

		dbf.setValidating(false);

		dbf.setNamespaceAware(true);

		LOGGER.trace("Preparando el schema factory...");
		
		sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		try {
			LOGGER.trace("Preparando el schema a partir del fichero xsd [" + xsd.getAbsolutePath() + "]");
			schema = sf.newSchema(xsd);
			
			dbf.setSchema(schema);
			db = dbf.newDocumentBuilder();

			LOGGER.trace("Comenzando el parseo del fichero xml [" + xml.getAbsolutePath()+"]");
			documento = db.parse(xml);
			LOGGER.trace("Terminado el parseo del fichero xml [" + xml.getAbsolutePath()+"]");
			
		} catch (SAXException | ParserConfigurationException | IOException e) {
			LOGGER.error("Error durante el parseo del XML [" + e.getMessage() + "]");
			return false;
		}

		return true;
	}

	public static Alumno getAlumnoExpediente(String expediente) {
		XPathFactory xf = XPathFactory.newInstance();
		XPath xpath = xf.newXPath();

		Alumno alumno = null;

		String expresion = "/alumnos/alumno[expediente='" + expediente + "']";

		try {
			Node nodoAlumno = (Node) xpath.evaluate(expresion, documento, XPathConstants.NODE);
			Element elementAlumno = (Element) nodoAlumno;

			String exp = nodoAlumno.getChildNodes().item(0).getTextContent();
			String nom = nodoAlumno.getChildNodes().item(1).getTextContent();
			int edad = Integer.parseInt(nodoAlumno.getChildNodes().item(2).getTextContent());
			String curso = elementAlumno.getAttribute("curso");
			String instituto = elementAlumno.getAttribute("IES");
			System.out.println(instituto);

			alumno = new Alumno(expediente, nom, edad, instituto, curso);

		} catch (XPathExpressionException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return alumno;
	}

	public static String getAlumnoNombre(String expediente) {

		XPathFactory xf = XPathFactory.newInstance();
		XPath xpath = xf.newXPath();

		Alumno alumno = null;

		String expresion = "/alumnos/alumno[expediente='" + expediente + "']";
		String nombreAlumno;

		try {
			Node nodoNombre = (Node) xpath.evaluate(expresion, documento, XPathConstants.NODE);

			if (nodoNombre != null) {
				nombreAlumno = nodoNombre.getTextContent();
			}

		} catch (XPathExpressionException e) {
			System.err.println("Error" + e.getMessage());
		}

		return null;
	}

	public static List<Alumno> getAlumnosCurso(String curso) {

		XPathFactory xf = XPathFactory.newInstance();
		XPath xpath = xf.newXPath();

		List<Alumno> alumnos = new ArrayList<Alumno>();

		String expresion = "/alumnos/alumno[expediente='" + curso + "']";

		try {
			NodeList listaNodos = (NodeList) xpath.evaluate(expresion, documento, XPathConstants.NODESET);

			for (int i = 0; i < listaNodos.getLength(); i++) {
				Node nodoAlumno = listaNodos.item(i);

				if (nodoAlumno.getNodeType() == Node.ELEMENT_NODE) {
					Element elementAlumno = (Element) nodoAlumno;

					String exp = nodoAlumno.getChildNodes().item(0).getTextContent();
					String nom = nodoAlumno.getChildNodes().item(1).getTextContent();
					int edad = Integer.parseInt(nodoAlumno.getChildNodes().item(2).getTextContent());
					String instituto = elementAlumno.getAttribute("IES");
					System.out.println(instituto);

					Alumno alumno = new Alumno(exp, nom, edad, instituto, curso);
					alumnos.add(alumno);
				}
			}

		} catch (XPathExpressionException e) {
			System.err.println("Error" + e.getMessage());
		}

		return alumnos;
	}

}
