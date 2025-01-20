package es.ciudadescolar.flujobinario;

public class Main {

	public static void main(String[] args) {

		FlujoBinario1 fb = new FlujoBinario1("abecedario.dat");
		
		fb.crearFichero();
		fb.leerFichero();
	}

}
