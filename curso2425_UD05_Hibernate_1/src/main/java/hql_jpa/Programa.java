package hql_jpa;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class Programa {

	private static final Logger log = LoggerFactory.getLogger(Programa.class);

	private static EntityManagerFactory factoria = Persistence.createEntityManagerFactory("PersistenciaPeliculas");

	public static void main(String[] args) {

		EntityManager manager = null;
		EntityTransaction trans = null;

		try {
			manager = factoria.createEntityManager();

			Query consultaJacobo = manager.createQuery("FROM hql_jpa.Actor a where a.nombre = :name");
			consultaJacobo.setParameter("name", "Jacobo");

			List<?> actores = consultaJacobo.getResultList();

			for (Object actor : actores) {
				log.info("Actor recuperado con HQL: " + actor.toString());
			}

			Query consultaActorDireccion = manager
					.createQuery("SELECT a.nombre, d.calle, d.num FROM hql_jpa.Actor a, hql_jpa.Direccion"
							+ "INNER JOIN a.direc d WHERE a.nombre = :name");
			consultaActorDireccion.setParameter("name", "Jacobo");

			List<?> actoresDirecciones = consultaActorDireccion.getResultList();

			for (int i = 0; i < actoresDirecciones.size(); i++) {
				Object[] registro = (Object[]) actoresDirecciones.get(i);
				String nom = (String) registro[0];
				String calle = (String) registro[1];
				Integer num = (Integer) registro[2];

				log.info("Registro recuperado de HQL: " + nom + "-" + calle + "-" + num);
			}

			// Para realizar modificacines y que bajen a la BD, necesito operar bajo una transaccion
			trans = manager.getTransaction();

			trans.begin();

			Query altaDirreccion = manager
					.createQuery("INSERT INTO hql_jpa.Direccion (calle, num) VALUES (:calledir, :numdir)");
			altaDirreccion.setParameter("calledir", "Calle FCB");
			altaDirreccion.setParameter("numdir", 5);

			if (altaDirreccion.executeUpdate() == 1)
				log.info("Se ha dado de alta por HQL correctamente la direccion");
			else
				log.warn("No se ha dado de alta por HQL la direccion");

			trans.commit();

			trans = manager.getTransaction();

			trans.begin();

			Query cambiaDirreccion = manager.createQuery(
					"UPDATE hql_jpa.Direccion SET calle  = :nuevaCalle WHERE calle = :viejaCalle AND num = :numCalle");
			cambiaDirreccion.setParameter("nuevaCalle", "Calle Madrid");
			cambiaDirreccion.setParameter("viejaCalle", "Calle FBC");
			cambiaDirreccion.setParameter("num", 5);

			if (cambiaDirreccion.executeUpdate() > 0)
				log.info("Se han actualizado las direcciones por HQL correctamente la direccion");
			else
				log.warn("No se han actualizado las direcciones por HQL la direccion");

			trans.commit();

			/*
			 * ATENCION Para borrar en cascada con HQL necesito que exista en la BD una restriccion
			 * "ON DELETE CASCADE" NO HARA CASO DE LA ANOTACION (@OneToOne(cascade =
			 * CascadeType.REMOVE) NI (@OneToOne(cascade = Cascade = CascadeType.ALL)
			 */
			trans = manager.getTransaction();

			trans.begin();

			Query borraActor = manager.createQuery("DELETE hql_jpa.Actor WHERE nombre = :nomActor");
			borraActor.setParameter("nomActor", "Santiago Segura");

			if (borraActor.executeUpdate() > 0)
				log.info("Se ha borrado el actor Santiago Segura por HQL correctamente"); // Al tener FK con ON DELETE CASCADE, tambien deberia borrar su direccion
			else
				log.warn("No se ha borrado el actor Santiago Segura por HQL");

			trans.commit();

		} catch (IllegalStateException e) {
			log.error("Error durante la creacion del contexto de persistencia [" + e.getMessage() + "]");

		} catch (PersistenceException e) {
			log.error("Error durante la persistencia (inserccion, actualizacion o borrado) de registros ["
					+ e.getMessage() + "]");
			if ((trans != null) && (trans.isActive()))
				trans.rollback();
		}

		finally {
			log.debug("Cerrando contexto de persistencia");
			if (manager.isOpen())
				manager.close();
			log.debug("Cerrando factoria de persistencia");
			if (factoria.isOpen())
				factoria.close();
		}
	}
}
