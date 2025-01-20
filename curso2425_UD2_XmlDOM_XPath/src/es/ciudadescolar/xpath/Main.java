package es.ciudadescolar.xpath;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {

		LOGGER.trace("Comienzo de la aplicacion");

		if (args.length != 2) {
			LOGGER.error("Error de parametros, esperados [2], pasados [" + args.length + "]");
			System.exit(1);
		} else {

			File xml = new File(args[0]);
			LOGGER.trace("El fichero xml pasado por parametro es [" + xml.getAbsolutePath() + "]");
			File xsd = new File(args[1]);
			LOGGER.trace("El fichero xsd pasado por parametro es [" + xsd.getAbsolutePath() + "]");

			if (XmlManager.parsearXmlDomXsd(xml, xsd)) {

				LOGGER.info("Se ha parseado correctamente el xml");
				LOGGER.info("Se va a consultar por Xpath el alumno con el expediente [33333]");
				LOGGER.trace("Se ha consultado por Xpath el alumno con el expediente [33333]");

				LOGGER.info(XmlManager.getAlumnoExpediente("33333").toString());

				LOGGER.info(XmlManager.getAlumnoExpediente("11111").toString());

				Alumno a = XmlManager.getAlumnoExpediente("22222");

				if (a != null)
					LOGGER.info(a.toString());
				else
					LOGGER.warn("No se ha encontrado alumno con expediente [55555]");

				String nombreAl = XmlManager.getAlumnoNombre("11111");

				if (nombreAl != null)
					LOGGER.trace("El nombre del alumno [11111] es " + nombreAl);

				for (Alumno alumno : XmlManager.getAlumnosCurso("24-25"))
					LOGGER.trace(alumno.toString());

			} else
				LOGGER.error("Error durante el parseo del xml");
		}
	}
}
