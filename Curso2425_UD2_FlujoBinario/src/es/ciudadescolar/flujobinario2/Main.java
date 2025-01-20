package es.ciudadescolar.flujobinario2;

import java.io.File;

public class Main {

	public static void main(String[] args) {

		Alumno[] alumnos = { new Alumno("1", "Paco", "aaa"), new Alumno("2", "Manolo", "ddd"),
				new Alumno("3", "Fermin", "ccc") };

		File fichero = new File("alumnos.dat");
		FlujoBinario2 fb = new FlujoBinario2(fichero);

		fb.escribirAlumnos(alumnos); // Serializamos objetos de la clase Alumnos

		fb.leerAlumnos(); // Deserializamos objetos de la clase Alumno

	}

}
