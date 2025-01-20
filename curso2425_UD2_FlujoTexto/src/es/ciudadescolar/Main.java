package es.ciudadescolar;

public class Main {

	public static void main(String[] args) {

		FlujoTexto ft = new FlujoTexto("ficheroTexto.txt");

		System.out.println("Creando fichero de texto...");
		ft.escribirFichero();

		System.out.println("Leyendo fichero de texto...\n");
		ft.leerFichero();

		String delimitador = "#";
		System.out.println("Procesando campos con StringTokenizer y delimitador [" + delimitador + "]...");
		ft.procesarCamposFichero(delimitador);
	}

}
