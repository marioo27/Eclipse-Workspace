package es.ciudadescolar;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * La clase main permite guardar y recuperar instancias de la clase alumnbo en una BBDD orientada a
 * objetos (db4a)
 * 
 * @author Mario Garcia
 * @version 2024/11/06
 */
public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {

		Alumno alumno, alumno2, alumno3;
		BBDDOO bddoo, bddoo2, bddoo3;

		if (args.length != 1) {
			log.error("Error de parametros. Se esperaba [nombre fichero BBDDOO]");
			System.exit(1);
		}

		File ficheroDB = new File(args[0]);

		bddoo = new BBDDOO(ficheroDB, true);

		alumno = new Alumno("9999", "Paco", 20, "DAM2", "IES Ciudad Escolar");
		alumno2 = new Alumno("8888", "Manolo", 22, "DAM2", "IES Ciudad Escolar");
		alumno3 = new Alumno("7777", "Fermin", 30, "DAM2", "IES Ciudad Escolar");

		// Guardado transaccional de las tres instancias
		try {
			bddoo.guardarAlumno(alumno);
			bddoo.guardarAlumno(alumno2);
			bddoo.guardarAlumno(alumno3);
			// Commit
			bddoo.comit();

			Alumno alumnoBuscado = bddoo.getAlumnoPorExpediente("9999");
			if (alumnoBuscado != null)
				log.info("Alumno encontrado [" + alumnoBuscado + "]");
			else
				log.info("Alumno no encontrado [9999]");
		} catch (Exception e) {
			// Rollback
			bddoo.rolback();
			log.error(e.getMessage());
		}

		finally {
			bddoo.cerrar();
		}

		log.debug("Abriendo BDD abierta sin borrar...");
		bddoo2 = new BBDDOO(ficheroDB, false);

		bddoo2.visualizarTodosAlumnos();

		bddoo2.cerrar();

		bddoo3 = new BBDDOO(ficheroDB, false);

		String expedienteCambioEdad = "9999";
		int edadAlumnoCambio = 24;

		log.debug("Se procede a cambiar la edad del alumno con expediente [" + expedienteCambioEdad + "]");
		bddoo3.cambiarEdadAlumno(new Alumno(expedienteCambioEdad, null, 0, null, null), edadAlumnoCambio);

		bddoo3.visualizarTodosAlumnos();

		bddoo3.borrarTodosAlumnos();

		bddoo3.visualizarTodosAlumnos();

		bddoo3.cerrar();

	}

}