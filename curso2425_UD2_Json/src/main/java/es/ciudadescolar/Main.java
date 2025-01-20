package es.ciudadescolar;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {

		LOG.info("JSON!");

		JSONObject alumnoJSON;

		alumnoJSON = new JSONObject();

		double[] notas = { 8.5, 7, 5.75 };

		alumnoJSON.put("Nombre", "Manolo");
		alumnoJSON.put("Edad", 20);
		alumnoJSON.put("Notas", notas);

		LOG.debug(alumnoJSON.toString());
		System.out.println(alumnoJSON.toString());
		System.out.println("El nombre del alumno es [" + alumnoJSON.getString("Nombre") + "]");

		JSONObject alumnoJSON2;

		alumnoJSON2 = new JSONObject();

		double[] notas2 = { 4.3, 4.5, 5 };

		alumnoJSON2.put("Nombre", "Fermín");
		alumnoJSON2.put("Edad", 19);
		alumnoJSON2.put("Notas", notas2);

		LOG.debug(alumnoJSON.toString());
		System.out.println(alumnoJSON.toString());
		System.out.println("El nombre del alumno es [" + alumnoJSON.getString("Nombre") + "]");

		JSONArray arrayDeAlumnos = new JSONArray();

		arrayDeAlumnos.put(alumnoJSON);
		arrayDeAlumnos.put(alumnoJSON2);

		for (int i = 0; i < arrayDeAlumnos.length(); i++)
			LOG.debug(arrayDeAlumnos.getJSONObject(i).toString());
	}

}
