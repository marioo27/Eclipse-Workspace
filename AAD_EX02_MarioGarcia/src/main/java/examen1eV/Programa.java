package examen1eV;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.Hacker;

public class Programa {
	private static final Logger log = LoggerFactory.getLogger(Programa.class);

	public static void main(int[] args) {

		if (args.length != 1) {
			log.error("Error, se esperaba la nota a comparar por parametro");
			System.exit(1);
		}

		int nota = args[0];

		File DB = new File("hackers.db4o");

		ArrayList<Hacker> listaHackers = new ArrayList<Hacker>();

		DBManager bbdd = new DBManager(DB, true, listaHackers);

		bbdd.infoHackers(nota, listaHackers);

	}

}
