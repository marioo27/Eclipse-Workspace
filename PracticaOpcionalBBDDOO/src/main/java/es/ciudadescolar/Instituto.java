package es.ciudadescolar;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que representa un Instituto, el cual contiene informacion basica sobre el centro educativo,
 * como su nombre, id y el listado de alumnos matriculados. Permite gestionar la matricula de
 * alumnos y la expulsion de los mismos.
 * 
 * @autor Mario Garcia
 * @version 1.0.0
 */
public class Instituto {

	private static final Logger log = LoggerFactory.getLogger(Instituto.class);

	/** Nombre del centro educativo */
	private String nombre;

	/** Identificador unico del instituto */
	private String idInstituto;

	/** Lista de alumnos matriculados en el instituto */
	private List<Alumno> listadoAlumnos;

	/**
	 * Constructor de la clase Instituto.
	 * 
	 * @param nombre         El nombre del instituto
	 * @param idInstituto    El identificador unico del instituto
	 * @param listadoAlumnos La lista de alumnos matriculados en el instituto
	 */
	public Instituto(String nombre, String idInstituto, List<Alumno> listadoAlumnos) {
		this.nombre = nombre;
		this.idInstituto = idInstituto;
		this.listadoAlumnos = listadoAlumnos;
	}

	/**
	 * Obtiene el nombre del centro educativo.
	 * 
	 * @return El nombre del centro
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del centro educativo.
	 * 
	 * @param nombre El nuevo nombre del centro
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el identificador unico del instituto.
	 * 
	 * @return El id del instituto
	 */
	public String getIdInstituto() {
		return idInstituto;
	}

	/**
	 * Establece el identificador unico del instituto.
	 * 
	 * @param idInstituto El nuevo identificador del instituto
	 */
	public void setIdInstituto(String idInstituto) {
		this.idInstituto = idInstituto;
	}

	/**
	 * Obtiene el listado de alumnos matriculados en el instituto.
	 * 
	 * @return La lista de alumnos
	 */
	public List<Alumno> getListadoAlumnos() {
		return listadoAlumnos;
	}

	/**
	 * Establece el listado de alumnos matriculados en el instituto.
	 * 
	 * @param listadoAlumnos La nueva lista de alumnos
	 */
	public void setListadoAlumnos(List<Alumno> listadoAlumnos) {
		this.listadoAlumnos = listadoAlumnos;
	}

	/**
	 * Matricula un nuevo alumno en el instituto.
	 * 
	 * @param al El alumno a matricular
	 */
	public void matricularAlumno(Alumno al) {
		listadoAlumnos.add(al);
		log.trace("Alumno " + al + " matriculado");
	}

	/**
	 * Expulsa un alumno del instituto.
	 * 
	 * @param al El alumno a expulsar
	 */
	public void expulsarAlumno(Alumno al) {
		listadoAlumnos.remove(al);
		log.trace("Alumno " + al + " expulsado");
	}

	/**
	 * Cambia el identificador unico del instituto.
	 * 
	 * @param nuevoId El nuevo identificador del instituto
	 * @return true si el cambio de id esta implementado, false si no
	 * @deprecated
	 */
	public boolean cambiarIdInstituto(int nuevoId) {

		try {
			setIdInstituto(idInstituto);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}

	}
}
