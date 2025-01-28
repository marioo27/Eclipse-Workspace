package one2many_uni_jpa;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "one2many_uni_jpa.Director")
@Table(name = "directores", schema = "peliculas_orm_2425")
public class Director implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cod_director")
	private int codigo_director;
	
	@Column(name = "nombre")
	private String nombre_director;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
	@JoinColumn(name = "cod_dire")
	private Set<Pelicula> pelis_director;
	
	public Director() {
		pelis_director = new HashSet<Pelicula>();
	}

	/**
	 * @return the codigo_director
	 */
	public int getCodigo_director() {
		return codigo_director;
	}

	/**
	 * @param codigo_director the codigo_director to set
	 */
	public void setCodigo_director(int codigo_director) {
		this.codigo_director = codigo_director;
	}

	/**
	 * @return the nombre_director
	 */
	public String getNombre_director() {
		return nombre_director;
	}

	/**
	 * @param nombre_director the nombre_director to set
	 */
	public void setNombre_director(String nombre_director) {
		this.nombre_director = nombre_director;
	}

	/**
	 * @return the pelis_director
	 */
	public Set<Pelicula> getPelis_director() {
		return pelis_director;
	}

	/**
	 * @param pelis_director the pelis_director to set
	 */
	public void setPelis_director(Set<Pelicula> pelis_director) {
		this.pelis_director = pelis_director;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo_director);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Director other = (Director) obj;
		return codigo_director == other.codigo_director;
	}

	@Override
	public String toString() {
		return "Director [codigo_director=" + codigo_director + ", nombre_director=" + nombre_director
				+ ", pelis_director=" + pelis_director + "]";
	}
}
