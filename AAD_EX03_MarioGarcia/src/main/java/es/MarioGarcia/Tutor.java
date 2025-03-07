package es.MarioGarcia;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tutores")
public class Tutor implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido")
	private String apellido;

	@Column(name = "email")
	private String email;

	@Column(name = "telefono")
	private String telefono;

	@Column(name = "tipo")
	private String tipo;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "empresa")
	private Empresa em;

	/**
	 * Construcctor sin parametros
	 */
	public Tutor() {
	}

	/**
	 * Construcctor con parametros
	 * 
	 * @param nombre
	 * @param apellido
	 * @param email
	 * @param telefono
	 * @param tipo
	 */
	public Tutor(String nombre, String apellido, String email, String telefono, String tipo) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.telefono = telefono;
		this.tipo = tipo;
	}

	/**
	 * Metodos Getters y Setters
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Empresa getEm() {
		return em;
	}

	public void setEm(Empresa em) {
		this.em = em;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/**
	 * Metodos HashCode y Equals, valorando solo el id
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tutor other = (Tutor) obj;
		return Objects.equals(id, other.id);
	}

	/**
	 * Metodo toString evitando bucles infinitos al no mostrar la informacion el atributo 'em'
	 */
	@Override
	public String toString() {
		return "Id: " + id + ", Nombre: " + nombre + ", Apellido: " + apellido + ", Email: " + email + ", Telefono: "
				+ telefono + ", tipo " + tipo;
	}

}
