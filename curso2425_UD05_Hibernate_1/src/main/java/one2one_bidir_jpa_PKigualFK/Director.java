package one2one_bidir_jpa_PKigualFK;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "one2one_bidir_jpa_PKigualFK.Director")
@Table(name = "directores", schema = "peliculas_orm_2425")
public class Director implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cod_director")
	private int codigo_director;

	@Column(name = "nombre")
	private String nombre_director;

	@OneToOne(mappedBy = "dir", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private NacionalidadDirector nacionalidad;

	public Director() {

	}

	public int getCodigo_director() {
		return codigo_director;
	}

	public void setCodigo_director(int codigo_director) {
		this.codigo_director = codigo_director;
	}

	public String getNombre_director() {
		return nombre_director;
	}

	public void setNombre_director(String nombre_director) {
		this.nombre_director = nombre_director;
	}

	public NacionalidadDirector getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(NacionalidadDirector nacionalidad) {
		this.nacionalidad = nacionalidad;

		if ((nacionalidad != null) && (nacionalidad.getDir() != this))
			nacionalidad.setDir(this);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo_director, nacionalidad, nombre_director);
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
		return codigo_director == other.codigo_director && Objects.equals(nacionalidad, other.nacionalidad)
				&& Objects.equals(nombre_director, other.nombre_director);
	}

	@Override
	public String toString() {
		return "Director [codigo_director=" + codigo_director + ", nombre_director=" + nombre_director
				+ ", nacionalidad=" + nacionalidad + "]";
	}

}
