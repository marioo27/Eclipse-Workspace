package es.ciudadescolar.xmlsax;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class XmlManager {

	protected static File ficheroXml;
	protected static File ficheroXsd;
	protected static List<Alumno> alumnos;

	public static boolean validarFicheros() {

		if (!ficheroXml.exists() || !ficheroXml.isFile()) {
			System.err.println("Error con el fichero de entrada XML");
			return false;
		}

		if (!ficheroXsd.exists() || !ficheroXsd.isFile()) {
			System.err.println("Error con el fichero de entrada XSD");
			return false;
		}

		if (!ficheroXml.getName().toLowerCase().endsWith("xml")) {
			System.err.println("Error con la extension del fichero de entrada XML");
			return false;
		}

		if (!ficheroXsd.getName().toLowerCase().endsWith("xsd")) {
			System.err.println("Error con la extension del fichero de entrada XSD");
			return false;
		}

		return true;
	}

	public static List<Alumno> parsearXmlSAX() {

		SAXParserFactory spf = SAXParserFactory.newInstance();
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = null;

		SAXParser sp = null;

		AlumnosDefaultHandler alumnosDH = null;

		try {
			schema = sf.newSchema();
			spf.setSchema(schema);
			sp = spf.newSAXParser();
			alumnosDH = new AlumnosDefaultHandler();
			
			sp.parse(ficheroXml, alumnosDH);
			
			alumnos = alumnosDH.getAlumnos();

		} catch (SAXException | ParserConfigurationException | IOException e) {
			System.err.println("Error" + e.getMessage());
		} 

		return alumnos;
	}

}
