package examen1eV;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import es.ciudadescolar.Hacker;

public class DBManager {

	private static final Logger log = LoggerFactory.getLogger(DBManager.class);
	ObjectContainer oc = null;

	public DBManager(File nombreDB, Boolean overwrite, ArrayList<Hacker> listaHackers) {
		oc = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), nombreDB.getName());

		if (overwrite && nombreDB.exists()) {
			log.debug("La BBDD ya existe, vamos a sobreescribirla");
			oc.delete(listaHackers);
		}

		for (Hacker h : listaHackers) {
			oc.store(h);
		}

	}

	public void infoHackers(int nota, ArrayList<Hacker> listaHackers) {

		int numHackers = 0;
		int numHackersTotal = 0;

		for (Hacker h : listaHackers) {
			oc.queryByExample(h);
			numHackersTotal++;

			log.debug("Inicialmente hay " + numHackersTotal + " en la BBDD ");
		}

		for (Hacker h : listaHackers) {
			if (h.getNota() >= nota) {
				oc.delete(h);
				listaHackers.remove(h);
				numHackers++;

				if (numHackers == 0) {
					log.warn("No se han encontrado coincidencias, ningun Hacker alcanza la nota " + nota);
					oc.close();
				} else {
					log.trace("Se han borrado " + numHackers + " de la BBDD ");
					log.trace("Quedan " + (numHackersTotal - numHackers) + " Hackers en la BBDD ");
					log.trace("Se procede a la creacion del fichero de salida");

					try {
						FileWriter fw = new FileWriter("hackers_superior_" + nota + "MarioGarcia.txt");
						PrintWriter pw = new PrintWriter(fw);

						for (Hacker h1 : listaHackers)
							pw.println(h1.getId() + "|" + h1.getNombre() + "|" + h1.getApellido() + "|" + nota);

						oc.close();
						pw.close();
					} catch (IOException e) {
						log.error("Error " + e.getMessage());
					}
				}
			}
		}

	}

}
