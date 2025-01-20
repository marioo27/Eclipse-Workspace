package es.ciudadescolar.flujobinario;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FlujoBinario1 {

	private String nombreFichero;
	private String abecedario;

	public FlujoBinario1(String nombFich) {
		nombreFichero = nombFich;
		abecedario = "abcdefghijklmnopqrstuvwxyz";
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public String getAbecedario() {
		return abecedario;
	}

	public void setAbecedario(String abecedario) {
		this.abecedario = abecedario;
	}

	public void crearFichero() {

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		DataOutputStream dos = null;

		try {

			fos = new FileOutputStream(nombreFichero);
			bos = new BufferedOutputStream(fos);
			dos = new DataOutputStream(bos);

			for (int i = 0; i < abecedario.length(); i++)
				dos.writeChar(abecedario.charAt(i));

		} catch (FileNotFoundException e) {
			System.out.println("Error. Fichero no encontrado");
		} catch (IOException e) {
			System.out.println("Error. Problemas durante el acceso a disco");
		} finally {

			try {

				dos.flush();
				dos.close();

			} catch (IOException e) {
				System.out.println("Error durante el cierre del fichero");
			}
		}

	}

	public void leerFichero() {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;

		try {
			fis = new FileInputStream(nombreFichero);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			// Leyendo char a char (tipo elemental -> DataInputStream)
			while (true) { // de este bucle infinito salimo con la excepcion EOFException (al llegar al
							// final del fichero)
				char caracter = dis.readChar();
				System.out.print(caracter + " ");
			}

		} catch (EOFException e) {
			// Excepcion controlada. No es un error. Es el final del fichero.
		} catch (FileNotFoundException e) {

			System.out.println("Error. fichero no accesible");
		} catch (IOException e) {
			System.out.println("Error accediendo al fichero para leer");
			
		} finally {

			try {
				dis.close();
				
			} catch (IOException e) {
				System.out.println("Error durante el cierre del fichero");
			}
		}
	}

}
