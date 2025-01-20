package es.ciudadescolar;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BBDDOO {

	private static final Logger log = LoggerFactory.getLogger(BBDDOO.class);
	
	public void guardarAlumno(Alumno al) {
		
	}
	
	public List<Alumno> getTodosAlumnos(String nombreInstituto){
		return null;
		
	}
	
	public boolean guardarInstituto(Instituto insti) {
		return false;
		
	}
	
	public Instituto getInstituto(Instituto institutoBuscado) {
		return institutoBuscado;
		
	}
	
	public boolean borrarInstituto(Instituto institutoABorrar) {
		return false;
		
	}
	
	public boolean expulsarATodosAlumnos (String nombreInstituto) {
		return false;
		
	}
	
	public Instituto consultaMatriculasInstituto(String idInstituto) {
		return null;
		
	}
	
	public Instituto consultaInstiMatriculado(String nomAlumno) {
		return null;
		
	}

}
