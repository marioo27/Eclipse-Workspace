package es.ciudadescolar;

public class Hacker {

	private int nota;
	private String id;
	private String nombre;
	private String apellido;

	public Hacker(int nota, String id, String nombre, String apellido) {

		this.nota = nota;
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

}
