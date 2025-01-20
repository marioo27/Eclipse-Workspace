package es.ciudadescolar.flujobinario2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FlujoBinario2 {

	private File ficheroBinario2 = null;

	public FlujoBinario2(File fich) {
		ficheroBinario2 = fich;
	}

	public void escribirAlumnos(Alumno[] alumnos) {

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;

		try {

			fos = new FileOutputStream(ficheroBinario2);
			bos = new BufferedOutputStream(fos);
			oos = new ObjectOutputStream(bos);

			for (Alumno a : alumnos) {
				oos.writeObject(a);
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error" + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error" + e.getMessage());

		} finally {

			try {
				oos.flush();
				oos.close();
			} catch (IOException e) {
				System.out.println("Error" + e.getMessage());
			}
		}
	}

	public void leerAlumnos() {

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;

		try {

			fis = new FileInputStream(ficheroBinario2);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);

			while (true) {
				Alumno a = (Alumno) ois.readObject();
				System.out.println(a);
			}
		} catch (EOFException e) {
			// Excepcion controlada. Fin de fichero
		} catch (ClassNotFoundException e) {
			System.out.println("Error" + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error" + e.getMessage());

		} finally {

			try {
				ois.close();
			} catch (IOException e) {
				System.out.println("Error" + e.getMessage());
			}
		}
	}
}
