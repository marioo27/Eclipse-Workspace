package es.cuidadescolar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
	
		Alumno al;
		List<Alumno> alumnos;
		JSONArray alumnosJsonArray;
		JSONObject alumnoJsonObject;
		File ficheroJsonSalida;

		if (args.length != 2) {
			LOG.error("Se esperaba un parametro [JSON entrada][JSON salida]");
			System.exit(1);
		}

		File ficheroJsonEntrada = new File(args[0]);
		ficheroJsonSalida = new File(args[1]);

		LOG.debug("El fichero pasado como parametro es [" + ficheroJsonEntrada.getAbsolutePath() + "]");

		if (JsonManager.parsearJsonFile(ficheroJsonEntrada)) {

			JSONObject objetoJsonRaiz = JsonManager.getObjetoJson();

			// LOG.trace("El numero de pares dentro del Json es [" + objetoJsonRaiz.length() + "]");

			alumnos = new ArrayList<Alumno>();

			alumnosJsonArray = objetoJsonRaiz.getJSONArray("alumnos");

			for (int i = 0; i < alumnosJsonArray.length(); i++) {
				alumnoJsonObject = alumnosJsonArray.getJSONObject(i);
				al = new Alumno(alumnoJsonObject.getInt("expediente"), alumnoJsonObject.getString("nombre"),
						alumnoJsonObject.getInt("edad"), objetoJsonRaiz.getString("curso"),
						objetoJsonRaiz.getString("centro"));
				
				alumnos.add(al);
			}
			
			JsonManager.escribirJsonFile(ficheroJsonSalida, alumnos);

			if (!JsonManager.visualizarJsonFile())
				LOG.warn("No es posible visualizar el fichero JSON");

		} else {
			LOG.warn("Durante el parseo hubo algun tipo de error");
		}

	}

}
