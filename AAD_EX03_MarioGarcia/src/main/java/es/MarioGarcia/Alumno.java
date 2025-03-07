package es.MarioGarcia;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "alumnos")
public class Alumno implements Serializable {

	/*
	 * En la relacion entre Alumno y Contacto he identificado una estructura OneToOne bidireccional,
	 * ya que doy por hecho que la informacion de un alumno contenida en la tabla 'alumnoscontactos'
	 * es unica a un alumno especifico. La primary Key de la tabla 'alumnos' (Id) es la Foreign Key
	 * de la tabla 'alumnoscontactos'
	 */

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombreyapellido")
	private String NomApe;

	@Column(name = "fecha_nacimiento")
	private LocalDate fechaNac;

	@OneToOne(cascade = CascadeType.ALL)
	private Contacto contact;

	/**
	 * Construcctor sin parametros
	 */
	public Alumno() {

	}

	/**
	 * Construcctor con parametros
	 * 
	 * @param nomApe
	 * @param fechaNac
	 * @param contact
	 */
	public Alumno(String nomApe, LocalDate fechaNac, Contacto contact) {
		NomApe = nomApe;
		this.fechaNac = fechaNac;
		this.contact = contact;
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

	public String getNomApe() {
		return NomApe;
	}

	public void setNomApe(String nomApe) {
		NomApe = nomApe;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	public Contacto getContact() {
		return contact;
	}

	public void setContact(Contacto contact) {
		this.contact = contact;
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
		Alumno other = (Alumno) obj;
		return Objects.equals(id, other.id);
	}

	/**
	 * Metodo toString
	 */
	@Override
	public String toString() {
		return "Datos del Alumno\t Id: " + id + ", Nombre y Apellidos: " + NomApe + ", Fecha de Nacimiento: " + fechaNac
				+ ", Datos de contacto: " + contact;
	}

}
