package curso2425_UD3A01_pizzas;

import java.util.Map;
import java.util.Objects;

public class Pizza {

	private String nombre;
	private float precio;
	private Map<Ingrediente, Double> ingredientes;

	public Pizza(String nombre, float precio, Map<Ingrediente, Double> ingredientes) {
		this.nombre = nombre;
		this.precio = precio;
		this.ingredientes = ingredientes;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Map<Ingrediente, Double> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(Map<Ingrediente, Double> ingredientes) {
		this.ingredientes = ingredientes;
	}

	@Override
	public String toString() {
		return "Pizza [nombre=" + nombre + ", precio=" + precio + ", ingredientes=" + ingredientes + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ingredientes, nombre, precio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pizza other = (Pizza) obj;
		return Objects.equals(ingredientes, other.ingredientes) && Objects.equals(nombre, other.nombre)
				&& Float.floatToIntBits(precio) == Float.floatToIntBits(other.precio);
	}

}
