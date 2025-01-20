package es.ciudadescolar;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Programa {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbManager.class);

	public static void main(String[] args) {

		DbManager db = new DbManager();

		Collection<Alumno> listaAlumnos = db.getAlumnos();
		if (listaAlumnos == null) {
			LOGGER.warn("No se ha recuperado ningun alumno");
		} else {
			for (Alumno alumno : listaAlumnos) {
				LOGGER.info(alumno.toString());
			}
		}
		String expedienteBuscado = "2";
		int nota = db.getNota(expedienteBuscado);
		if (nota < 0) {
			LOGGER.warn("No se ha encontrado ningun alumno con expediete [" + expedienteBuscado + "]");
		} else {
			LOGGER.info("La nota del alumno con expediente[" + expedienteBuscado + "]: [" + nota + "]");
		}
		System.out.println("---------------");
		db.mostrarAlumnos();
		System.out.println("---------------");

//		db.cambiarNota("1", 10);
//		db.cambiarNombre("1", "Francisco");
//		Alumno alumno = new Alumno("6","Mael", 0);
//		db.insertarAlumno(alumno);
//		db.borrarAlumno(alumno);

		db.muestraInfoAlumno("1");
		LOGGER.info("La nota de raices del alumno con expediente 2:" + db.getNotaAlumno("2"));
		LOGGER.info("Alta de 3 alumnos hardcoded transaccionalmente");
		
		if (db.altaAlumnosTrans()) {
			LOGGER.debug("");
		}
		db.cerrarDb();
	}

}
