package es.cuidadescolar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonManager {

	private static JSONObject objetoJson;

	private static Logger LOG = LoggerFactory.getLogger(JsonManager.class);

	public static JSONObject getObjetoJson() {
		return objetoJson;
	}

	public static void setObjetoJson(JSONObject objetoJson) {
		JsonManager.objetoJson = objetoJson;
	}

	public static boolean parsearJsonFile(File ficheroJson) {

		FileReader fr = null;
		BufferedReader br = null;
		String linea = "";
		StringBuilder sb = null;

		if (!ficheroJson.exists()) {
			LOG.error("El fichero [" + ficheroJson.getAbsolutePath() + "] no existe");
			return false;
		}
		if (!ficheroJson.canRead()) {
			LOG.error("No se dispone de permisos de lectura sobre el fichero [" + ficheroJson.getAbsolutePath() + "]");
			return false;
		}

		try {
			fr = new FileReader(ficheroJson);
			br = new BufferedReader(fr);
			sb = new StringBuilder();

			LOG.debug("Comenzando a leer el fichero [" + ficheroJson.getAbsolutePath() + "]");
			while ((linea = br.readLine()) != null) {
				sb.append(linea);
			}
			LOG.debug("FInalizada la lectura del fichero [" + ficheroJson.getAbsolutePath() + "]");

		} catch (FileNotFoundException e) {
			LOG.error("El fichero [" + ficheroJson.getAbsolutePath() + "] no existe");
		} catch (IOException e) {
			LOG.error("Error leyendo el fichero [" + ficheroJson.getAbsolutePath() + "]");
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					LOG.warn("Error cerrando el fichero [" + ficheroJson.getAbsolutePath() + "]");
				}
		}
		if (sb != null && !sb.isEmpty()) {
			objetoJson = new JSONObject(sb.toString());
			return true;
		} else
			return false;

	}

	public static boolean visualizarJsonFile() {

		if (objetoJson != null) {
			LOG.debug(objetoJson.toString(2));
			return true;
		}
		LOG.warn("Imposible visualizar el fichero Json");
		return false;
	}

	public static boolean visualizarJsonFile(JSONObject objetoJsonEntrada) {

		if (objetoJsonEntrada != null) {
			LOG.debug(objetoJsonEntrada.toString(2));
			return true;
		}
		LOG.warn("Imposible visualizar el fichero Json");
		return false;

	}

	public static boolean escribirJsonFile(File ficheroJsonSalida, List<Alumno> listaAlumnos) {

		PrintWriter pw = null;

		JSONArray arrayAlumnos = null;
		JSONObject objetoAlumno = null;

		if (ficheroJsonSalida.exists()) {
			LOG.warn("El fichero de salida [" + ficheroJsonSalida.getAbsolutePath() + "] ya existe");
			return false;
		}

//		if (!ficheroJsonSalida.canWrite()) {
//			LOG.error("No hay permisos de escritura en el fichero de salida [" + ficheroJsonSalida.getAbsolutePath()
//					+ "]");
//			return false;
//		}

		try {
			pw = new PrintWriter(ficheroJsonSalida);

			arrayAlumnos = new JSONArray();

			for (Alumno al : listaAlumnos) {
				objetoAlumno = new JSONObject();

				objetoAlumno.put("expedient", al.getExpediente());
				objetoAlumno.put("name", al.getNombre());
				objetoAlumno.put("age", al.getEdad());
				objetoAlumno.put("high school", al.getInstituto());
				objetoAlumno.put("school year", al.getCurso());

				arrayAlumnos.put(objetoAlumno);
			}

			if (arrayAlumnos.length() > 0) {
				pw.print(arrayAlumnos);
			}

		} catch (FileNotFoundException e) {
			LOG.error("El fichero de salida no se encuentra");
		} finally {

			if (pw != null) {
				pw.flush();
				pw.close();
			}
		}
		if (arrayAlumnos.isEmpty()) {
			LOG.warn("");
			return false;
		} else {
			LOG.debug("Fichero Json creado satisfactoriamente");
			return true;
		}
	}

}
