package es.ciudadescolar;

import java.util.Objects;

public class Actor {

	private boolean protagonista;
	private String nombre;
	private String apellido;

	public Actor(boolean protagonista, String nombre, String apellido) {
		this.protagonista = protagonista;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public boolean isProtagonista() {
		return protagonista;
	}

	public void setProtagonista(boolean protagonista) {
		this.protagonista = protagonista;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Override
	public String toString() {
		return "Actor [protagonista=" + protagonista + ", nombre=" + nombre + ", apellido=" + apellido + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(apellido, nombre, protagonista);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		return Objects.equals(apellido, other.apellido) && Objects.equals(nombre, other.nombre)
				&& protagonista == other.protagonista;
	}

}
