package es.aad;

import java.time.LocalDate;
import java.util.Objects;

public class Persona {

	private String nombre;
	private String email;
	private LocalDate fechaNac;

	public Persona(String nombre, String email, LocalDate fechaNac) {
		this.nombre = nombre;
		this.email = email;
		this.fechaNac = fechaNac;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, fechaNac, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(email, other.email) && Objects.equals(fechaNac, other.fechaNac)
				&& Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", email=" + email + ", fechaNac=" + fechaNac + "]";
	}

}
