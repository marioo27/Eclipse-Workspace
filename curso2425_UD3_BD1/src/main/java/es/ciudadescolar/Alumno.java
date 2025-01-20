package es.ciudadescolar;

import java.util.Objects;

public class Alumno {

	private String expediente, nombre;
	private int nota;
	
	public Alumno(String expediente, String nombre, int nota) {
		this.expediente = expediente;
		this.nombre = nombre;
		this.nota = nota;
	}
	
	public Alumno() {
		
	}
	
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getNota() {
		return nota;
	}
	public void setNota(int nota) {
		this.nota = nota;
	}
	
	@Override
	public String toString() {
		return "Alumno [expediente=" + expediente + ", nombre=" + nombre + ", nota=" + nota + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(expediente, nombre, nota);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alumno other = (Alumno) obj;
		return Objects.equals(expediente, other.expediente) && Objects.equals(nombre, other.nombre)
				&& nota == other.nota;
	}
	
	
	
}
