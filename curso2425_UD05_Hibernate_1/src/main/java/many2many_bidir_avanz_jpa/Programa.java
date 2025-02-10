package many2many_bidir_avanz_jpa;

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

		Actor actor1 = null;
		Actor actor2 = null;
		Pelicula peli = null;
		Protagonista prot1 = null;
		Protagonista prot2 = null;

		try {
			manager = factoria.createEntityManager();

			actor1 = new Actor("Santiago Segura", new Direccion("Calle sinfin", 12));
			actor2 = new Actor("Toni Acosta", new Direccion("Avenida del final", 7));

			peli = new Pelicula("Padre no hay mas que uno", LocalDate.of(2019, 8, 1));

			ClaveProtagonista claveProt1 = new ClaveProtagonista(peli.getCodigo_pelicula(), actor1.getCodigo_actor());

			prot1 = new Protagonista();
			prot1.setClave(claveProt1);
			prot1.setActor(actor1);
			prot1.setPelicula(peli);
			prot1.setEsProta(Boolean.TRUE);

			ClaveProtagonista claveProt2 = new ClaveProtagonista(peli.getCodigo_pelicula(), actor1.getCodigo_actor());

			prot2 = new Protagonista();
			prot2.setClave(claveProt2);
			prot2.setActor(actor2);
			prot2.setPelicula(peli);
			prot2.setEsProta(Boolean.FALSE);

			actor1.aniadirColaboracion(prot1);
			peli.aniadirColaboracion(prot1);

			actor2.aniadirColaboracion(prot2);
			peli.aniadirColaboracion(prot2);

			transacccion = manager.getTransaction();
			transacccion.begin();

			manager.persist(actor1);
			manager.persist(actor2);
			manager.persist(peli);
			manager.persist(prot1);
			manager.persist(prot2);

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
