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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "alumnoscontactos")
public class Contacto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "email")
	private String email;

	@Column(name = "telefono")
	private String telefono;

	@OneToOne(mappedBy = "contact", cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	private Alumno alum;

	/**
	 * Construcctor sin parametros
	 */
	public Contacto() {

	}

	/**
	 * Construcctor con parametros
	 * 
	 * @param email
	 * @param telefono
	 */
	public Contacto(String email, String telefono) {
		this.email = email;
		this.telefono = telefono;
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

	public Alumno getAlum() {
		return alum;
	}

	public void setAlum(Alumno alum) {
		this.alum = alum;
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
		Contacto other = (Contacto) obj;
		return Objects.equals(id, other.id);
	}

	/**
	 * Metodo toString evitando bucles infinitos al no mostrar la informacion el atributo 'alum'
	 */
	@Override
	public String toString() {
		return "Id de Contacto: " + id + ", Email: " + email + ", Telefono: " + telefono;
	}

}
