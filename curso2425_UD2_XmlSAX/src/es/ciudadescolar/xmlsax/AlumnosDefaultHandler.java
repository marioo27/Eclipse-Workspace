package es.ciudadescolar.xmlsax;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class AlumnosDefaultHandler extends DefaultHandler {

	private List<Alumno> alumnos;
	private Alumno alumno;

	private StringBuilder sb; // La usaremos para guardar el texto recuperado con cada evento "characters"
	
	// Constantes que representan la etiquetas en el xml
	private final String EDAD = "edad";
	private final String CURSO = "curso";
	private final String NOMBRE = "nombre";
	private final String ALUMNO = "alumno";
	private final String INSTITUTO = "IES";
	private final String EXPEDIENTE = "expediente";
	

	@Override
	public void startDocument() throws SAXException {
		System.out.println("Comenzando procesamiento del fichero XML: Inicio documento");
		alumnos = new ArrayList<Alumno>();
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("Finalizando procesamiento del fichero XML: Fin de documento");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println("Comienzo del elemento [" + qName + "]");

		if (attributes.getLength() > 0)
			for (int i = 0; i < attributes.getLength(); i++)
				System.out.println("Atributo [" + attributes.getQName(i) + "] = " + attributes.getValue(i));

		if (qName.equalsIgnoreCase(ALUMNO)) {
			alumno = new Alumno();
			alumno.setCurso(attributes.getValue(CURSO));
			alumno.setInstituto(attributes.getValue(INSTITUTO));
		}
		
		sb = new StringBuilder(); // Reseteo la instanciacion inicial del string builder con cada elemento

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println("Final del elemento [" + qName + "]");
		//Aqui es donde yo sabre que informacion tengo en el sb
		
		switch(qName) {
			case ALUMNO -> alumnos.add(alumno);
			case NOMBRE -> alumno.setNombre(sb.toString());
			case EXPEDIENTE -> alumno.setExpediente(sb.toString());
			case EDAD -> alumno.setEdad(Integer.parseInt(sb.toString()));
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		String texto = String.valueOf(ch, start, length).trim(); // Elimina los espacios en blanco

		if (!texto.isEmpty()) // Solo imprime si no esta vacio
			System.out.println("Texto localizado [" + texto + "]");
		
		sb.append(ch, start, length);
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		System.err.println("Se ha detectado un error " + e.getMessage());
	}

	public List<Alumno> getAlumnos() {

		return alumnos;
	}

}
