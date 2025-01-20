package es.ciudadescolar.flujobinario2;

import java.io.Serializable;

public class Alumno implements Serializable {

	private static final long serialVersionUID = 1L;

	private String expediente;
	private String nombre;
	private transient String password; // No queremos que se serialice el atributo password

	public Alumno(String exp, String nom, String pass) {
		this.expediente = exp;
		this.nombre = nom;
		this.password = pass;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Alumno [expediente=" + expediente + ", nombre=" + nombre + ", password=" + password + "]";
	}

}
