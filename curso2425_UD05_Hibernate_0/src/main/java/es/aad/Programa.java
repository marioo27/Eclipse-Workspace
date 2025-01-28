package es.aad;

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
		cliente1.setEmail("carlos5@miempresa.es");
		cliente1.setFechaRegistro(LocalDateTime.now());
		cliente1.setTelefono("911121122");

		// cliente1 es una instancia de la clase cliente y esta en estado transitorio
		// (es decir, solo esta en RAM)

		EntityTransaction transaccion = null;

		try {
			transaccion = em.getTransaction();

			transaccion.begin();

			em.persist(cliente1);
			// a partir de aqui cliente1 esta administrado

			cliente1.setEmail("martin6@miempresa.es");
			//este cambio se propaga por la BD
			
			//si por lo que sea, queremos descartar los datos realizados por el objeto
			//y recargar los datos de la BD...
			em.refresh(cliente1);
			
			transaccion.commit();
			LOG.info("Se ha guardado correctamente el objeto cliente: " + cliente1.toString());

			// una vez persistido el objeto, el SGBD le asignado id (auto_increment)
			LOG.info("Se ha guardado con el id: " + cliente1.getIdCliente());

			Cliente cliente2 = new Cliente();
			cliente2.setNombre("Manolo");
			cliente2.setEmail("manolo2@miempresa.es");
			cliente2.setTelefono("919992229");

			transaccion = em.getTransaction();

			transaccion.begin();

			// si sabemos cierto que el objeto es transitorio, debemos hacer el persist
			// si dudamos, podriamos hacer un merge: si no existe lo inserta y si no existe
			// lo actualiza
			// EL MERGE NO CAMBIA EL ESTADO DEL OBJETO A "ADMINISTRADO"
			// EL MERGE DEVUELVE EL OBJETO ADMINISTRADO

			// em.persist(cliente2);
			em.merge(cliente2);

			cliente2.setEmail("manolo3@miempresa.es");

			// si queremos que el cambiuo de email se lleve a la bbdd antes del detach,
			// tenemos que hacer el fluch()
			em.flush();
			em.detach(cliente2);// el detach saca del contexto de persistencia el objeto administrado pasado
								// como parametro
			// si queremos sacar del contexto de persistencia TODOS los objetos
			// administrados puedo hacer em.clear();

			// este cambio posterior no se persistira (no ira a la BD)
			cliente2.setEmail("manolo4@miempresa.es");
			transaccion.commit();

			em.close();

			// Creamos un nuevo entityManager(EM) -> Los objetos que estaban administrados
			// en la EM previa, pasan a estado separado
			em = emf.createEntityManager();

			// supongamos que queremos borrar el cliente2 (esta en modo setached)
			// usaremos el metodo find() que busca por PK y que devuelve un objeto
			// administrado o NULL si no lo encuentra
			Cliente cliente3 = em.find(Cliente.class, cliente2.getIdCliente());

			if (cliente3 != null) {
				LOG.info("Cliente encontrado:" + cliente3.toString());

				// siempre que queramos modificar la BDD, necesito una transaccion
				transaccion = em.getTransaction();

				transaccion.begin();

				em.remove(cliente3);

				transaccion.commit();
			} else {
				LOG.info("El cliente no ha sido localizado" + cliente2.getIdCliente());
			}

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
