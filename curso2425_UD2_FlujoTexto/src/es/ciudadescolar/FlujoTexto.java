package es.ciudadescolar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class FlujoTexto {

	private File fichero;

	public FlujoTexto(String nomFich) {

		fichero = new File(nomFich);
	}

	public void leerFichero() {

		FileReader fr = null;
		BufferedReader br = null;

		try {

			fr = new FileReader(fichero);
			br = new BufferedReader(fr);

			String linea = br.readLine();

			while (linea != null) {
				System.out.println(linea);
				linea = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error durante la lectura del fichero (inexistente)");
		} catch (IOException e) {
			System.out.println("Error durante la lectura del fichero");
		} finally {

			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("Error durante el cierre de fichero");
				}
		}
	}

	public void escribirFichero() {

		FileWriter fw = null;
		PrintWriter pw = null;

		try {

			fw = new FileWriter(fichero);
			pw = new PrintWriter(fw);

			for (int i = 1; i < 11; i++)
				pw.println("linea#" + i + "#texto");

			pw.flush();

		} catch (IOException e) {
			System.out.println(
					"Error durante el acceso para escritura para el fichero [" + fichero.getAbsolutePath() + "]");
		} finally {
			if (pw != null)
				pw.close();

		}
	}

	public void procesarCamposFichero(String delimitador) {

		FileReader fr = null;
		BufferedReader br = null;

		StringTokenizer stk = null;

		String campo1, campo3;
		int campo2;

		try {

			fr = new FileReader(fichero);
			br = new BufferedReader(fr);

			String linea = br.readLine();

			while (linea != null) {
				stk = new StringTokenizer(linea, delimitador);

				if (stk.countTokens() == 3) {
					campo1 = stk.nextToken();
					campo2 = Integer.parseInt(stk.nextToken());
					campo3 = stk.nextToken();
					System.out.println(campo1 + "---" + campo2 + "---" + campo3);
				}

				linea = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error durante la lectura del fichero (inexistente)");
		} catch (IOException e) {
			System.out.println("Error durante la lectura del fichero");
		} finally {

			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("Error durante el cierre de fichero");
				}
		}
	}

}
