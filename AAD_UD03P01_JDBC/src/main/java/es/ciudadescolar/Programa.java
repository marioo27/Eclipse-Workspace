package es.ciudadescolar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Programa {

	public static void main(String[] args) {
		
		File ficheroXml = new File("peliculas.xml");
		File ficheroXsd = new File("peliculas.xsd");
		
		List<Pelicula> listaPeliculas = XmlManager.procesarXml(ficheroXml, ficheroXsd);
		
		DbManager db = new DbManager();
		
		db.cerrarDb();
		

	}

}
