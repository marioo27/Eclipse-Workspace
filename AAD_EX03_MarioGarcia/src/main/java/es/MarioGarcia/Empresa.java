package es.MarioGarcia;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "empresas")
public class Empresa implements Serializable {

	/*
	 * En la relacion entre Empresa y Turor he identificado una estructura ManyToOne bidireccional,
	 * ya que doy por hecho que la una empresa puede tener varios tutores pero un tutor solo puede
	 * tener una empresa. La primary Key de la tabla 'empresas' (Id) es la Foreign Key de la tabla
	 * 'turores'
	 */

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "sector")
	private String sector;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "email_contacto")
	private String email;

	@Column(name = "telefono_contacto")
	private String telefono;

	@OneToMany(mappedBy = "em", cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	private Tutor tutor;

	/**
	 * Construcctor sin parametros
	 */
	public Empresa() {
	}

	/**
	 * Construcctor con parametros
	 * 
	 * @param nombre
	 * @param sector
	 * @param direccion
	 * @param email
	 * @param telefono
	 * @param tutor
	 */
	public Empresa(String nombre, String sector, String direccion, String email, String telefono, Tutor tutor) {
		this.nombre = nombre;
		this.sector = sector;
		this.direccion = direccion;
		this.email = email;
		this.telefono = telefono;
		this.tutor = tutor;
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

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public Tutor getTutor() {
		return tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	/**
	 * Metodos HashCode y Equals, valorando solo el id
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		return Objects.equals(id, other.id);
	}

	/**
	 * Metodo toString
	 */
	@Override
	public String toString() {
		return "Datos de la Empresa\t Id: " + id + ", Nombre:" + nombre + ", Sector: " + sector + ", Direccion: "
				+ direccion + ", Email: " + email + ", Telefono: " + telefono + ", Datos del Tutor: " + tutor;
	}

}
