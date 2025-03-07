package one2one;

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

@Entity(name = "one2one.Cliente")
@Table(name = "customers", schema = "empresa_orm_2324")
public class Cliente implements Serializable {
	/*
	 * He decidido realizar el apartado 3.1, una estructura de datos con Cardinalidad 1:1 Para creo
	 * optimo usar una estructura OneToOne unidireccional ya que doy por hecho que en cada direccion
	 * solo vive un cliente, al igual que el numero de telefono. Tambien he optado por usar esta
	 * estructura ya que en la tabla customer_details la Primary Key es la misma que la Foreign Key
	 */

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private int idCliente;

	@Column(name = "first_name")
	private String nombre;

	@Column(name = "last_name")
	private String apellido;

	@Column(name = "email")
	private String email;

	@OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private DetallesCliente detalles;

	/**
	 * Constructor por defecto vacio
	 */
	public Cliente() {

	}

	/**
	 * Constructor con parametros No se tiene en cuenta 'idCliente' ya que en la Base de Datos es un
	 * campo autoincremental
	 * 
	 * @param nombre
	 * @param apellido
	 * @param email
	 */
	public Cliente(String nombre, String apellido, String email) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
	}

	/**
	 * Getter del atributo 'IdCliente', devuelve dicho atributo
	 * 
	 * @return idCliente
	 */
	public int getIdCliente() {
		return idCliente;
	}

	/**
	 * Setter del atributo 'IdCliente', cambia el valor de dicho atributo por el pasado por
	 * parametro
	 * 
	 * @param idCliente
	 */
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * Getter del atributo 'nombre', devuelve dicho atributo
	 * 
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Setter del atributo 'nombre', cambia el valor de dicho atributo por el pasado por
	 * parametro
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Getter del atributo 'apellido', devuelve dicho atributo
	 * 
	 * @return apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * Setter del atributo 'apellido', cambia el valor de dicho atributo por el pasado por
	 * parametro
	 * 
	 * @param apellido
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * Getter del atributo 'email', devuelve dicho atributo
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter del atributo 'email', cambia el valor de dicho atributo por el pasado por
	 * parametro
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter del atributo 'detalles', devuelve dicho atributo
	 * 
	 * @return detalles
	 */
	public DetallesCliente getDetalles() {
		return detalles;
	}

	/**
	 * Setter del atributo 'detalles', cambia el valor de dicho atributo por el pasado por
	 * parametro
	 * 
	 * @param detalles
	 */
	public void setDetalles(DetallesCliente detalles) {
		this.detalles = detalles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCliente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return idCliente == other.idCliente;
	}

	@Override
	public String toString() {
		return "Cliente con ID:" + idCliente + ", Nombre:" + nombre + ", Apellido:" + apellido + ", Email:" + email
				+ ", Detalles:" + detalles + "]";
	}

}
