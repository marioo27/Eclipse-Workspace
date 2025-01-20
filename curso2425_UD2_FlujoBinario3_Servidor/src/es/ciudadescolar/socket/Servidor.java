package es.ciudadescolar.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	private final int PUERTO = 4444;
	private ServerSocket serverSocket = null;
	private Socket clienteSocket = null;

	public Servidor() {

		try {
			serverSocket = new ServerSocket(PUERTO); // Socket servidor
			clienteSocket = new Socket(); // Socket cliente

		} catch (IOException e) {
			System.out.println("Error en el estableciomiento de la conexion");
		}
	}

	public void arrancarServidor() {

		System.out.println("Esperando peticiones del cliente...");

		try {
			clienteSocket = serverSocket.accept();
			System.out.println("Cliente en linea");

			Alumno[] AlumnosRecibidos = obtenerAlumnos();

			for (Alumno a : AlumnosRecibidos)
				System.out.println(a);

			System.out.println("Finalizando comunicacion con el cliente");
			clienteSocket.close();
			serverSocket.close();

		} catch (IOException e) {
			System.out.println("Error estableciendo comunicacion con el cliente");
		}
	}

	public Alumno[] obtenerAlumnos() {

		InputStream is = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;

		Alumno[] alumnos = null;

		try {
			is = clienteSocket.getInputStream();
			bis = new BufferedInputStream(is);
			ois = new ObjectInputStream(bis);

			Object objeto = ois.readObject();
			alumnos = (Alumno[]) objeto;
			ois.close();

		} catch (ClassNotFoundException e) {
			System.out.println("Error en el formato de la informacion");
		}

		catch (IOException e) {
			System.out.println("Error recibiendo el listado de alumnos");
		}

		return alumnos;
	}
}
