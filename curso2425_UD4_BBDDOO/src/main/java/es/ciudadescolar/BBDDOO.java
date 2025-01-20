package es.ciudadescolar;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db4o.ObjectSet;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

/**
 * La clase nos permite la interaccion con la BDD orientada a objetos que almacene alumnos
 * 
 * @author Mario Garcia
 * @version 2024/11/06
 */
public class BBDDOO {
	private static final Logger log = LoggerFactory.getLogger(BBDDOO.class);

	private ObjectContainer db;

	private File ficheroDB;

	public BBDDOO(File ficheroDB, boolean overwrite) {
		this.ficheroDB = ficheroDB;
		if (overwrite) {
			// Dado que vamos a ejecutar la aplicacion muchas veces, queremos que con cafa ejecucion se cree de
			// nuevo la BDD
			if (ficheroDB.exists()) {
				log.warn("BDD existente. Se procede a borrar");
				ficheroDB.delete();
			}
		}
		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), ficheroDB.getName());
		log.info("BDD abierta... [" + ficheroDB.getAbsolutePath() + "]");
	}

	public boolean cerrar() {
		if (db != null) {
			db.close();
			log.info("BDD cerrada correctamente... [" + ficheroDB.getAbsolutePath() + "]");
			return true;
		}
		log.warn("No se puede cerrar la BDD porque no estaba inicializada");
		return false;
	}

	public boolean guardarAlumno(Alumno al) {
		try {
			db.store(al);
			log.debug("Se ha guardado correctamente el alumno [" + al);
			return true;
		} catch (Exception e) {
			log.error("Error guardando alumno. [" + e.getMessage() + "]");
		}
		return false;
	}

	public List<Alumno> getTodosAlumnos() {
		return db.queryByExample(Alumno.class);
	}

	public Alumno getAlumnoPorExpediente(String exp) {
		// La instancia de busqueda debe tener valores por defecto
		Alumno busqueda = new Alumno(exp, null, 0, null, null);
//		Alumno busqueda = new Alumno();// La otra opcion es inicializar el alumno vacio y dar los valores que quieres inicializar

		ObjectSet<Alumno> alumnosRecuperados = db.queryByExample(busqueda);

		Alumno al = null;
		if (alumnosRecuperados.size() > 0) {
			al = alumnosRecuperados.next();
			log.info(al.toString());
		} else {
			log.warn("No se ha encontrado alumnos con expediente [" + exp + "]");
		}

		return al;
	}

	public boolean cambiarEdadAlumno(Alumno plantillaAlumno, int nuevaEdad) {

		ObjectSet<Alumno> alumnosRecuperados = db.queryByExample(plantillaAlumno);

		if (alumnosRecuperados.size() == 1) {

			Alumno alumnoModificar = alumnosRecuperados.next();
			alumnoModificar.setEdad(nuevaEdad);
			db.store(alumnoModificar);
			log.debug("Se ha modificado correctamente el alumno.");
			return true;
		} else if (alumnosRecuperados.size() == 0) {

			log.warn("No hay coincidencias de alumnos...");
			return false;
		}
		log.warn("Se han encontrado varias coincidencias de alumnos...");
		return true;
	}

	public void visualizarTodosAlumnos() {

		List<Alumno> todosAlumnos = null;

		if (db != null) {
			todosAlumnos = this.getTodosAlumnos();
			log.info("Visualizando los alumnos obtenidos de la BDD");
			for (Alumno al : todosAlumnos) {
				log.info("Alumno encontrado [" + al + "]");
			}
		} else
			log.warn("Imposible visualizar alumnos. BDD sin inicializar");
	}

	/**
	 * Este metodo permite borrar todos los objetos de la clase Alumno en la BDD
	 * 
	 * @return true si al menos borra un objeto, false si no borra nada
	 */

	public boolean borrarTodosAlumnos() {

		List<Alumno> alumnosRecuperados = this.getTodosAlumnos();

		if (alumnosRecuperados.size() > 0) {

			for (Alumno alBorr : alumnosRecuperados) {
				db.delete(alBorr);
				log.debug("Se ha borrado un alumno");
			}
			return true;
		} else {

			log.warn("No se ha borrado ningun alumno, no hay alumnos guardados");
			return false;
		}

	}

	public boolean borrarAlumno(Alumno alumnoBorrar) {

		List<Alumno> alumnosRecuperados = db.queryByExample(alumnoBorrar);

		if (alumnosRecuperados.size() == 1) {

			db.delete(alumnoBorrar);
			log.debug("Se ha borrado correctamente el alumno");

			return true;
		} else if (alumnosRecuperados.size() == 0) {

			log.warn("No se ha borrado ningun alumno, no hay alumnos coincidentes");
			return false;
		}

		log.warn("Se han encontrado varias coincidencias, no se ha borrado nada");
		return true;
	}

	public void comit() {

		if (db != null) {
			db.commit();
			log.debug("Commit realizado en la BDD");
		}
	}

	public void rolback() {

		if (db != null) {
			db.rollback();
			log.debug("Rollback realizado en la BDD");
		}
	}
}