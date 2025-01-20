package Ficheros;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {

		Scanner sc = null;
		String nombreFichero = null;
		File fichero = null;
		RandomAccessFile ficheroAD = null;
		int posicion = -1;

		if (args.length == 1) {
			nombreFichero = args[0];
			fichero = new File(nombreFichero);
			if (fichero.exists()) {
				System.out.println("Fichero [" + nombreFichero + "] ya existe. Borrandolo");
				fichero.delete();
			}
			try {
				ficheroAD = new RandomAccessFile(fichero, "rw");
				for (int i = 1; i <= 100; i++)
					ficheroAD.writeByte(i);

				// Desplazamos el puntero a la posicion inicial del fichero
				ficheroAD.seek(0);

				sc = new Scanner(System.in);
				do {
					try {
						System.out.println(
								"Introduzca la posicion a leer del fichero [1 - " + ficheroAD.length() + "] -> ");
						posicion = sc.nextInt();
					} catch (InputMismatchException e) {
						sc.nextLine();
					}
				} while (posicion <= 1 || posicion > ficheroAD.length());
				sc.close();

				ficheroAD.seek(posicion - 1);
				System.out.println("Antes de leer el puntero está en la posicion [" + ficheroAD.getFilePointer() + "]");
				System.out.println("El byte leido es [" + ficheroAD.readByte() + "]");
				System.out.println("Después de leer el puntero está en la posicion [" + ficheroAD.getFilePointer() + "]");
				
				ficheroAD.close();


			} catch (FileNotFoundException e) {
				System.out.println("El fichero [" + nombreFichero + "] no se ha encomtrado " + e.getMessage());
			} catch (IOException e) {
				System.out.println("Error durante la escritura del fichero [" + nombreFichero + "] no se ha encomtrado "
						+ e.getMessage());
			}

		} else
			System.out.println("Error. Se esperaba por parametro el nombre de un fichero");
	}

}
