package es.ciudadescolar;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {

		List<Alumno> listadoAlumnos = new ArrayList<>();

		Instituto instituto = new Instituto("IES Ciudad Escolar", "12345", listadoAlumnos);
		Alumno alumno1 = new Alumno("A001", "Juan Perez", 16, "1º Bachillerato", "IES Ciudad Escolar");
		Alumno alumno2 = new Alumno("A002", "Maria Lopez", 15, "2º ESO", "IES Ciudad Escolar");
		Alumno alumno3 = new Alumno("A003", "Carlos Garcia", 17, "1º Bachillerato", "IES Ciudad Escolar");

		instituto.matricularAlumno(alumno1);
		instituto.matricularAlumno(alumno2);
		instituto.matricularAlumno(alumno3);

		instituto.expulsarAlumno(alumno2);

	}

}
