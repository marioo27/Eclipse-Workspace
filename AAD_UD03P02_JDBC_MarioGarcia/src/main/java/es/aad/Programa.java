package es.aad;

import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Programa {
	private static final Logger LOGGER = LoggerFactory.getLogger(Programa.class);

	public static void main(String[] args) {
		DbManager db = new DbManager();

		Persona p1 = new Persona("Paco", "paco1234@gmail.com", LocalDate.parse("1970-06-05"));
		Persona p2 = new Persona("Fermin", "ferminPlantas@hotmail.com", LocalDate.parse("1980-02-18"));
		Persona p3 = new Persona("Justo", "justoInjusto@gmail.com", LocalDate.parse("1965-12-31"));

		/*
		 * List<Persona> listaAmigos = new ArrayList<Persona>(); listaAmigos.add(p1); listaAmigos.add(p2);
		 * listaAmigos.add(p3);
		 */

		LOGGER.trace("A continuacion se va a realizar el alta de varias personas de forma transaccional");
		
		db.desactivarAutocomit();

		boolean inserccionP1 = db.aniadirPersona(p1.getNombre(), p1.getEmail(), p1.getFechaNac());
		boolean inserccionP2 = db.aniadirPersona(p2.getNombre(), p2.getEmail(), p2.getFechaNac());
		boolean inserccionP3 = db.aniadirPersona(p3.getNombre(), p3.getEmail(), p3.getFechaNac());

		if (inserccionP1 && inserccionP2 && inserccionP3) {
			db.comit();
			LOGGER.debug("Personas insertadas en la BDD con exito, se ejecuta commit");
		} else {
			db.rolback();
			LOGGER.warn("No se pudo insertar ninguna persona en la BDD ya que una o varias dieron error, se ejecuto rollback");
		}

		db.activarAutocomit();

		/*
		 * boolean transaccionPersonasExito = true;
		 * 
		 * for(Persona p : listaAmigos) { transaccionPersonasExito = db.aniadirPersona(p.getNombre(),
		 * p.getEmail(), p.getFechaNac()); if(!transaccionPersonasExito) db.rolback(); }
		 * 
		 * db.comit();
		 */
	}

}
