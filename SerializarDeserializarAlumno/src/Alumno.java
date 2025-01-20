
public class Alumno {

	private int edad;
	private String nia;
	private String nombre;
	
	public Alumno(int edad, String nia, String nombre) {
		super();
		this.edad = edad;
		this.nia = nia;
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getNia() {
		return nia;
	}

	public void setNia(String nia) {
		this.nia = nia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
