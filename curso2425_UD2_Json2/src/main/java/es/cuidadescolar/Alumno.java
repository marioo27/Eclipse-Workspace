package es.cuidadescolar;

public class Alumno {

	private int expediente;
	private String nombre;
	private int edad;
	private String curso;
	private String instituto; // IES es opcional, puede ser null

	public Alumno() {

	}

	public Alumno(int expediente, String nombre, int edad, String curso, String instituto) {
		this.expediente = expediente;
		this.nombre = nombre;
		this.edad = edad;
		this.curso = curso;
		this.instituto = instituto;
	}

	public int getExpediente() {
		return expediente;
	}

	public void setExpediente(int expediente) {
		this.expediente = expediente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getInstituto() {
		return instituto;
	}

	public void setInstituto(String ies) {
		this.instituto = ies;
	}

	public String toString() {
		return "Alumno [expediente=" + expediente + ", nombre=" + nombre + ", edad=" + edad + ", curso=" + curso
				+ (instituto != null ? (", " + instituto) : "") + "]";
	}

}
