package esciudadescolar.xml;

import java.io.File;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Erro. Debes pasarke la ruta del fichero xml");
			System.exit(1);
		}

		File ficheroXml = new File(args[0]);

		if (!ficheroXml.exists()) {
			System.out.println("Error de acceso al fichero xml. Fichero no existe");
			System.exit(2);
		}

		List<Alumno> alumnosRecuperados = XmlManager.procesarXml(ficheroXml);

		if (alumnosRecuperados.isEmpty()) {
			System.out.println("No se ha recuperado ningun alumno");
			System.exit(3);
		} else
			for (Alumno a : alumnosRecuperados)
				System.out.println(a);
		
		System.out.println("\nGenerando XML...");	
		XmlManager.generarNuevoXml(alumnosRecuperados);
	}
	
}
