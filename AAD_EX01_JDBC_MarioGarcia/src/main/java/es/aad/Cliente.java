package es.aad;

import java.time.LocalDate;
import java.util.Objects;

public class Cliente 
{
	private String nombre, email, telefono;
    private LocalDate fechaRegistro;
    
  
    public Cliente (String nombre, String email, String telefono)
    {
    	this.nombre = nombre;
    	this.telefono = telefono;
    	this.email = email;
    	this.fechaRegistro = LocalDate.now();

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


	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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
