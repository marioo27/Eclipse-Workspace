package es.ciudadescolar.socket;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {

	private String HOST = "localhost";
	private final int PUERTO = 4444;
	private Socket socket;

	public Cliente() {

		try {
			socket = new Socket(HOST, PUERTO);
			System.out.println("Conexion establecida con el servidor [" + socket + "]");
		} catch (UnknownHostException e) {
			System.out.println("Error. No se ha idenficado el host del servidor");
		} catch (IOException e) {
			System.out.println("Error en la comunicacion con el servidor");
		}
	}

	public void enviarAlumno(Alumno[] alumnos) {

		OutputStream os = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;

		System.out.println("Enviando alumnos...");

		try {
			os = socket.getOutputStream();
			bos = new BufferedOutputStream(os);
			oos = new ObjectOutputStream(bos);

			oos.writeObject(alumnos);

			oos.flush();
			oos.close();

			System.out.println("Alumnos enviados");

			socket.close();

		} catch (IOException e) {
			System.out.println("Error durante el envio de alumnos");
		}

	}

}
