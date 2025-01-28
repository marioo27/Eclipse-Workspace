package one2many_uni_jpa;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "one2many_uni_jpa.Pelicula")
@Table(name = "peliculas", schema = "peliculas_orm_2425")
public class Pelicula implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_peli")
	private int codigo_pelicula;
	
	@Column(name = "titulo")
	private String titulo_pelicula;
	
	@Column(name = "fecha_grabacion")
	private LocalDate fecha_grab;

	public Pelicula() {}
	
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

	/*
	Consideramos los 4 atributos porque el codigo no lo informamos explicitamente y 
	por tanto si tengo varias peliculas para un director todas ellas tienen codigo 
	null hasta que las persista la BD y como uso un set para guardar las peliculas 
	de un director , al reyenar ese set machaca las peliculas quedandose unicamente 
	la ultima añadida
	*/
	@Override
	public int hashCode() {
		return Objects.hash(codigo_pelicula, fecha_grab, titulo_pelicula);
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
		return codigo_pelicula == other.codigo_pelicula && 
				Objects.equals(fecha_grab, other.fecha_grab) && 
				Objects.equals(titulo_pelicula, other.titulo_pelicula);
	}

	@Override
	public String toString() {
		String director = "No hay director asignado";
		
		return "Pelicula [codigo_pelicula=" + codigo_pelicula + ", titulo_pelicula=" + titulo_pelicula + ", fecha_grab="
				+ fecha_grab + ", dir_pelicula=" + director + "]";
	}
	
	
}
