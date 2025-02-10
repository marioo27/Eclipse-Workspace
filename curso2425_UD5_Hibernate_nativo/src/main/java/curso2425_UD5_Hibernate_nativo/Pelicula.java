package curso2425_UD5_Hibernate_nativo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Pelicula implements Serializable {
	private static final long serialVersionUID = 1L;

	private int codigo_pelicula;

	private String titulo_pelicula;

	private LocalDate fecha_grab;

	private Director dir_pelicula;

	public Pelicula() {
	}

	public Pelicula(String titulo_pelicula, LocalDate fecha_grab) {
		this.titulo_pelicula = titulo_pelicula;
		this.fecha_grab = fecha_grab;
	}

	/**
	 * @return the codigo_pelicula
	 */
	public int getCodigo_pelicula() {
		return codigo_pelicula;
	}

	/**
	 * @param codigo_pelicula the codigo_pelicula to set
	 */
	public void setCodigo_pelicula(int codigo_pelicula) {
		this.codigo_pelicula = codigo_pelicula;
	}

	/**
	 * @return the titulo_pelicula
	 */
	public String getTitulo_pelicula() {
		return titulo_pelicula;
	}

	/**
	 * @param titulo_pelicula the titulo_pelicula to set
	 */
	public void setTitulo_pelicula(String titulo_pelicula) {
		this.titulo_pelicula = titulo_pelicula;
	}

	/**
	 * @return the fecha_grab
	 */
	public LocalDate getFecha_grab() {
		return fecha_grab;
	}

	/**
	 * @param fecha_grab the fecha_grab to set
	 */
	public void setFecha_grab(LocalDate fecha_grab) {
		this.fecha_grab = fecha_grab;
	}

	/**
	 * @return the dir_pelicula
	 */
	public Director getDir_pelicula() {
		return dir_pelicula;
	}

	/**
	 * @param dir_pelicula the dir_pelicula to set
	 */
	public void setDir_pelicula(Director dir_pelicula) {
		this.dir_pelicula = dir_pelicula;

		if ((dir_pelicula != null) && !(dir_pelicula.getPelis_director().contains(this)))
			dir_pelicula.addPelicula(this);
	}

	/*
	 * Consideramos los 4 atributos porque el codigo no lo informamos explicitamente y por tanto si
	 * tengo varias peliculas para un director todas ellas tienen codigo null hasta que las persista
	 * la BD y como uso un set para guardar las peliculas de un director , al reyenar ese set
	 * machaca las peliculas quedandose unicamente la ultima añadida
	 */
	@Override
	public int hashCode() {
		return Objects.hash(codigo_pelicula, dir_pelicula, fecha_grab, titulo_pelicula);
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
		return codigo_pelicula == other.codigo_pelicula && Objects.equals(dir_pelicula, other.dir_pelicula)
				&& Objects.equals(fecha_grab, other.fecha_grab)
				&& Objects.equals(titulo_pelicula, other.titulo_pelicula);
	}

	@Override
	public String toString() {
		String director = "No hay director asignado";
		if (dir_pelicula != null)
			director = dir_pelicula.getNombre_director();

		return "Pelicula [codigo_pelicula=" + codigo_pelicula + ", titulo_pelicula=" + titulo_pelicula + ", fecha_grab="
				+ fecha_grab + ", dir_pelicula=" + director + "]";
	}

}
