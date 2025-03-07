package es.ciudadescolar;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Programa {

	private static final String ApiURL = "http://localhost:8080/api/v1/alumnos";
	private static final Logger LOG = LoggerFactory.getLogger(Programa.class);

	public static void main(String[] args) {

		HttpClient cliente = null;
		HttpRequest peticion = null;
		Builder constructor = null;
		HttpResponse<String> respuesta = null;

		JSONArray listaAlumnos = null;

		cliente = HttpClient.newHttpClient();
		constructor = HttpRequest.newBuilder();
		constructor.uri(URI.create(ApiURL));
		constructor.header("Accept", "application/json");
		constructor.GET();

		peticion = constructor.build();

		try {
			respuesta = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());
			listaAlumnos = new JSONArray(respuesta.body());

			LOG.info(listaAlumnos.toString(2));
			// LOG.info(respuesta.body());

		} catch (IOException e) {
			LOG.error("Error en el acceso a la API: " + ApiURL + " [" + e.getMessage() + "]");
		} catch (InterruptedException e) {
			LOG.error("Error en el acceso a la API: " + ApiURL + " [" + e.getMessage() + "]");
		}

	}

}
