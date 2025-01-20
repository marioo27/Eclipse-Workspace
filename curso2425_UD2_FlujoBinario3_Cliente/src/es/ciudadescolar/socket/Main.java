package es.ciudadescolar.socket;

public class Main {

	public static void main(String[] args) {

		Alumno[] alumnos = { new Alumno("1", "Paco", "aaa"), new Alumno("2", "Manolo", "ddd"),
				new Alumno("3", "Fermin", "ccc") };

		Cliente cliente = new Cliente();

		cliente.enviarAlumno(alumnos);

		System.out.println("Fin de la comunicacion");
		
	}
}
