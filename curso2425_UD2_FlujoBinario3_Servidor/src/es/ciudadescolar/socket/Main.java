package es.ciudadescolar.socket;

public class Main {

	public static void main(String[] args) {

		Servidor server = new Servidor();
		
		System.out.println("Iniciando servidor");
		
		server.arrancarServidor();
						
	}
}
