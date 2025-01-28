package es.aad;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table (name ="clientes", schema = "crm")
public class Cliente implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	// siempre atributo con la PK
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)   // esto lo ponemos para indicar que la PK es auto_increment
	private Integer IdCliente;
	
	@Column (name = "Nombre")
	private String nombre;
	
	@Column (name = "Email")
	private String email;
	
	@Column (name = "Telefono")
	private String telefono;
	
	@Column (name = "FechaRegistro")
    private LocalDateTime fechaRegistro;
    
  
    public Cliente()
    {
    	
    }
    public Cliente (Integer id, String nombre, String email, String telefono)
    {
    	this.IdCliente = id;
    	this.nombre = nombre;
    	this.telefono = telefono;
    	this.email = email;
    	this.fechaRegistro = LocalDateTime.now();
    }

    public Integer getIdCliente() {
		return IdCliente;
	}
	
	public void setIdCliente(Integer idCliente) {
		IdCliente = idCliente;
	}
	
	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
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


	@Override
	public int hashCode() {
		return Objects.hash(email, fechaRegistro, nombre, telefono);
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
		return Objects.equals(email, other.email) && Objects.equals(fechaRegistro, other.fechaRegistro)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(telefono, other.telefono);
	}


	@Override
	public String toString() {
		return "Cliente [nombre=" + nombre + ", email=" + email + ", telefono=" + telefono + ", fechaRegistro="
				+ fechaRegistro + "]";
	}
    
}
