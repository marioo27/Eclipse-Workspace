package es.ciudadescolar.xmlsax;

import java.io.File;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println("Error, se esperaban dos parametros: [xml][xsd]");
			System.exit(1);
		}

		XmlManager.ficheroXml = new File(args[0]);
		XmlManager.ficheroXsd = new File(args[1]);

		if (!XmlManager.validarFicheros())
			System.exit(2);

		List<Alumno> alumnos = XmlManager.parsearXmlSAX();

		if (alumnos.size() == 0)
			System.err.println("No se ha recuperado ningun alumno");
		else
			for (Alumno a : alumnos)
				System.out.println(a);

	}

}
