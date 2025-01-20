package esciudadescolar.xml;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document; // Siempre usar el org.w3, no el javaSwing ni similar
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import org.xml.sax.SAXException;

public class XmlManager {

	public static List<Alumno> procesarXml(File fichero) {

		List<Alumno> listaAlumnos = new ArrayList<>();

		// Alumno a1 = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setValidating(false); // No validamos con DTD
		dbf.setNamespaceAware(true); // Validacion contra Schema (XSD)

		File ficheroXsd = new File("alumnos.xsd");
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		Schema schema = null;

		dbf.setIgnoringElementContentWhitespace(true); // Ignorar espacios en blanco sin informacion util

		try {

			schema = sf.newSchema(ficheroXsd);

			dbf.setSchema(schema);

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document documento = db.parse(fichero);

			Element raiz = documento.getDocumentElement(); // Recuperamos el elemento raiz del XML que se llama
															// "alumnos"
			System.out.println("Se dispone de " + raiz.getChildNodes().getLength() + " nodos");

			NodeList listaNodosAlumnos = raiz.getChildNodes();

			for (int i = 0; i < listaNodosAlumnos.getLength(); i++) {

				Node nodoAlumno = listaNodosAlumnos.item(i);

				if (nodoAlumno.getNodeType() == Node.ELEMENT_NODE) {
					Element elementoAlumno = (Element) nodoAlumno;
					Node nodoExpediente = elementoAlumno.getFirstChild(); // Esto nos devuelve el primer nodo del
																			// elemento alumno (expediente)
					Node nodoNombre = nodoExpediente.getNextSibling(); // Esto nos devuelve el siguiente nodo
																		// delelemento alumno (nombre)
					Node nodoEdad = elementoAlumno.getLastChild(); // Esto nos devuelve el ultimo (siguiente) nodo del
																	// elemento alumno (edad)

					Alumno a2 = new Alumno(nodoExpediente.getTextContent(), nodoNombre.getTextContent(),
					Integer.parseInt(nodoEdad.getTextContent()));
					listaAlumnos.add(a2);
				}
			}

		} catch (ParserConfigurationException e) {
			System.out.println("Error durante el parseo del XML " + e.getMessage());
		} catch (SAXException e) {
			System.out.println("Error durante el parseo del XML " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error durante el parseo del XML " + e.getMessage());
		}

		return listaAlumnos;
	}

	public static void generarNuevoXml(List<Alumno> alumnos) {

		// Lo primero es crear la estructura de arbol en la RAM (memoria principal)
		DocumentBuilderFactory dbf = null;
		DocumentBuilder db = null;
		Document documento = null;
		Element elementoAlumno = null;

		try {
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			documento = db.newDocument();

			Element raiz = documento.createElement("estudiantes");

			raiz.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			raiz.setAttribute("xsi:noNamespaceSchemaLocation", "alumnos2.xsd");

			documento.appendChild(raiz);

			for (Alumno a : alumnos) {
				elementoAlumno = documento.createElement("alumno");

				// Opcion 1 para anadir un atributo a un elemento
				Attr atributoNombre = documento.createAttribute("nom");
				atributoNombre.setNodeValue(a.getNombre());
				elementoAlumno.setAttributeNodeNS(atributoNombre);

				// Opcion 2
				Attr atributoExpediente = documento.createAttribute("exp");
				Text nodoTextoExpediente = documento.createTextNode(a.getExpediente());
				atributoExpediente.appendChild(nodoTextoExpediente);
				elementoAlumno.setAttributeNode(atributoExpediente);

				// Opcion 3
				elementoAlumno.setAttribute("edad", (String.valueOf(a.getEdad())));

				Element edadAlumno = documento.createElement("edad");
				Text nodoTextoEdad = documento.createTextNode(String.valueOf(a.getEdad()));

				edadAlumno.appendChild(nodoTextoEdad);
				elementoAlumno.appendChild(edadAlumno);

				raiz.appendChild(elementoAlumno);
			}

			// A partir de ahora lo que tenemos que hacer es volcar a fichero el arbol
			TransformerFactory tf = null;
			Transformer t = null;
			DOMSource ds = null;
			StreamResult sr = null;

			try {
				tf = TransformerFactory.newDefaultInstance();
				t = tf.newTransformer();
				ds = new DOMSource(documento);

				// Opcion 1 para el volcado como fichero binario sr = new StreamResult(new
				// FileOutputStream("alumnos2.xml")); Opcion 2
				sr = new StreamResult(new FileWriter("alumnos2.xml"));

				// Como nuestro XML de salida queremos que se valide contra un DTD (alumnos2.dtd) domImp =
				t.setOutputProperty(OutputKeys.METHOD, "xml");
				t.setOutputProperty(OutputKeys.VERSION, "1.0");
				t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				t.setOutputProperty(OutputKeys.INDENT, "yes");
				t.transform(ds, sr);

				// Opcion 3

			} catch (TransformerConfigurationException e) {
				System.out.println("Error generando el XML" + e.getMessage());
			} catch (FileNotFoundException e) {
				System.out.println("Error en el volcado del arbol DOM sobre el fichero XMl " + e.getMessage());
			} catch (TransformerException e) {
				System.out.println("Error en el volcado del arbol DOM sobre el fichero XMl " + e.getMessage());
			} catch (IOException e) {
				System.out.println("Error" + e.getMessage());
			}

		} catch (ParserConfigurationException e) {
			System.out.println("Error en la creacion del arbol DOM" + e.getMessage());
		}
	}

	public static List<String> recuperarExpedientes(File fichero) { // NodeList getElementByTagName(String etiqueta)A
		return null;
	}
}
