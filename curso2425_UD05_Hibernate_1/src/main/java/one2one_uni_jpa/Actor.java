package one2one_uni_jpa;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "actores", schema = "peliculas_orm_2425")
public class Actor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Atributos de la clase:
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_act")
	private int codigo_actor;
	
	@Column(name = "nombre")
	private String nombre;
	@OneToOne (cascade = CascadeType.PERSIST)
	//@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "cod_direccion") // Esta etiqueta nos sirve tmb para identificar esta entidad como OWNER/PADRE/MASTER
	private Direccion direc;
	
	public Actor() {
		
	}
	
	public Actor(String nombre, Direccion direc) {
		this.nombre = nombre;
		this.direc = direc;
	}

	/**
	 * @return the codigo_actor
	 */
	public int getCodigo_actor() {
		return codigo_actor;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @return the direc
	 */
	public Direccion getDirec() {
		return direc;
	}
	/**
	 * @param codigo_actor the codigo_actor to set
	 */
	public void setCodigo_actor(int codigo_actor) {
		this.codigo_actor = codigo_actor;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @param direc the direc to set
	 */
	public void setDirec(Direccion direc) {
		this.direc = direc;
	}
	@Override
	public int hashCode() {
		return Objects.hash(codigo_actor);
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
		return codigo_actor == other.codigo_actor;
	}
	@Override
	public String toString() {
		return "Actor [codigo_actor=" + codigo_actor + ", nombre=" + nombre + ", direc=" + direc + "]";
	}
	
	
}
