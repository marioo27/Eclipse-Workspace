package many2many_bidir_basic_jpa;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

public class Programa {

	private static final Logger LOG = LoggerFactory.getLogger(Programa.class);

	private static EntityManagerFactory factoria = Persistence.createEntityManagerFactory("PersistenciaPeliculas");

	public static void main(String[] args) {

		EntityManager manager = null;
		EntityTransaction transacccion = null;

		try {
			manager = factoria.createEntityManager();

			Actor actor1 = new Actor("Santiago Segura", new Direccion("Calle sinfin", 12));
			Actor actor2 = new Actor("Toni Acosta", new Direccion("Avenida del final", 7));

			Pelicula peli = new Pelicula("Padre no hay mas que uno", LocalDate.of(2019, 8, 1));

			peli.aniadirActor(actor1);
			peli.aniadirActor(actor2);

			actor1.aniadirPelicula(peli);
			actor2.aniadirPelicula(peli);

			transacccion = manager.getTransaction();

			transacccion.begin();
			manager.persist(peli);
			
			transacccion.commit();

		} catch (IllegalStateException e) {
			LOG.error("Error en la gestion de persistencia [" + e.getMessage() + "]");
		} catch (PersistenceException e) {
			LOG.error("Error en la persistencia de objetos [" + e.getMessage() + "]");

			if (transacccion.isActive())
				transacccion.rollback();
		}
	}

}
