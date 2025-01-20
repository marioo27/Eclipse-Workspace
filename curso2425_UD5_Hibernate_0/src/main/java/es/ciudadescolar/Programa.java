package es.ciudadescolar;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.aad.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

public class Programa {

	private static final Logger LOG = LoggerFactory.getLogger(Programa.class);

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenciaCRM");

	public static void main(String[] args) {

		EntityManager em = emf.createEntityManager();

		Cliente cliente1 = new Cliente();

		cliente1.setNombre("Carlos");
		cliente1.setEmail("carlos@miempresa.es");
		cliente1.setFechaRegistro(LocalDateTime.now());
		cliente1.setTelefono("911121122");

		// cliente1 es una instancia de la clase cliente y esta en estado transitorio (es decir, solo esta
		// en RAM)

		EntityTransaction transaccion = null;

		try {
			transaccion = em.getTransaction();

			transaccion.begin();

			em.persist(cliente1);
			// a partir de aqui cliente1 esta administrado

			cliente1.setEmail("carlos2@miempresa.es");
			// este cambio se propaga a la BD como un update

			transaccion.commit();
			LOG.info("Se ha guardado correctamente el objeto cliente: " + cliente1.toString());

			// una vez persistido el objeto, el SGBD le ha asignado el id (auto_increment)
			LOG.info("Se ha guardado con el id: " + cliente1.getClienteID());

			Cliente cliente2 = new Cliente();

			cliente2.setNombre("Manolo");
			cliente2.setEmail("manolo@miempresa.es");
			cliente2.setTelefono("919922228");

			transaccion = em.getTransaction();

			transaccion.begin();

			// si sabemos cierto que el objeto es transitorio debemos hacer el persisst si dudamos, podriamos
			// hacer un merge: si no existe lo inserta y si existe lo actualiza

			// EL MERGE NO CAMBIA EL ESTADO DEL OBJETO A ADMINISTRADO, EL MERGE DEVUELVE EL OBJERO ADMINISTRADO

			// em.persist(cliente2);
			cliente2 = em.merge(cliente2);

			cliente2.setEmail("manolo2@miempresa.es");

			// si queremos que el cambio de email se lleve a la BD antes del detach tenemos que hacer flush()
			em.flush();
			em.detach(cliente2);
			// el detach saca del contexto de persistencia el objeto pasado como parametro este cambio posterior
			// em.clear() saca del contexto de persistencia TODOS los objetos administrados

			// no persistira (no ira a la BD)
			cliente2.setEmail("manolo3@miempresa.es");

			LOG.info(cliente2.toString());
			transaccion.commit();

			em.close();

			// creamos un nuevo EM -> los objetos que estaban adiministrados en la EM previa pasan a estado separado
			em = emf.createEntityManager();

			// supongamos que queremos borrar el cliente2 (está en estado deteched) 
			//em.find(), cliente2);

		} catch (PersistenceException pe) {
			LOG.error("Error durante la persistencia de el objeto cliente." + pe.getMessage());

			if (transaccion.isActive()) {
				transaccion.rollback();
			}
		} finally {
			if (em.isOpen()) {
				em.close();
			}
			if (emf.isOpen()) {
				emf.close();
			}
		}

	}

}
