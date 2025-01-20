package es.ciudadescolar;

import java.util.List;
import java.util.Objects;

public class Pelicula {

	private String nombre;
	private int anio;
	private List<Actor> actores;

	public Pelicula(String nombre, int anio, List<Actor> actores) {
		this.nombre = nombre;
		this.anio = anio;
		this.actores = actores;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public List<Actor> getActores() {
		return actores;
	}

	public void setActores(List<Actor> actores) {
		this.actores = actores;
	}

	@Override
	public String toString() {
		return "Pelicula [nombre=" + nombre + ", anio=" + anio + ", actores=" + actores + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(actores, anio, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pelicula other = (Pelicula) obj;
		return Objects.equals(actores, other.actores) && anio == other.anio && Objects.equals(nombre, other.nombre);
	}

}
