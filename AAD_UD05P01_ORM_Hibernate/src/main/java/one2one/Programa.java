package one2one;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

public class Programa {

	private static EntityManagerFactory factoria = Persistence.createEntityManagerFactory("PersistenciaPeliculas");
	private static final Logger log = LoggerFactory.getLogger(Programa.class);
	private static Scanner sc;

	public static void main(String[] args) {

		Cliente paco = new Cliente("Paco", "Diaz", "pd1921@tocomocho.net");
		DetallesCliente detallesPaco = new DetallesCliente("Avda. Sin nombre, 121", "6496568991");

		EntityManager manager = null;
		EntityTransaction transaccion = null;

		try {
			manager = factoria.createEntityManager();
			transaccion = manager.getTransaction();
			altaCliente(paco, detallesPaco, manager, transaccion);

			manager = factoria.createEntityManager();

			cambiarNumTel(detallesPaco, manager, transaccion);

			manager = factoria.createEntityManager();

			buscarClientePorId(manager, transaccion);

			manager = factoria.createEntityManager();

			borrarCliente(paco, manager, transaccion);

		} catch (IllegalStateException e) {

			log.error("Error durante la gestion del contexto de persistencia");
		} catch (PersistenceException e) {

			log.error("Error durante la persistencia de objetos");
			if (transaccion.isActive())
				transaccion.rollback();
		} finally {

			if (manager != null && manager.isOpen())
				manager.close();
			if (factoria.isOpen())
				factoria.close();
		}
	}

	/**
	 * Metodo que borra un cliente de la Base de Datos
	 * 
	 * @param paco
	 * @param manager
	 * @param transaccion
	 */
	private static void borrarCliente(Cliente paco, EntityManager manager, EntityTransaction transaccion) {
		manager.remove(paco);

		transaccion.commit();
		log.info("Cliente 'Paco' borrado con exito");
		manager.close();
	}

	/**
	 * Metodo que busca y muestra la informacion de un cliente a partir de un identificador
	 * 
	 * @param manager
	 * @param transaccion
	 */
	private static void buscarClientePorId(EntityManager manager, EntityTransaction transaccion) {
		Cliente clienteBuscado = manager.find(Cliente.class, 3);

		if (clienteBuscado != null)
			log.info("Se ha encontrado un cliente con el identificador 3 [" + clienteBuscado.toString() + "]");

		transaccion.commit();
		manager.close();
	}

	/**
	 * Metodo que cambia el numero de telefono de un cliente
	 * 
	 * @param detallesPaco
	 * @param manager
	 * @param transaccion
	 */
	private static void cambiarNumTel(DetallesCliente detallesPaco, EntityManager manager,
			EntityTransaction transaccion) {
		String numNuevoPaco = "149656899";
		detallesPaco.setNumTel(numNuevoPaco);

		transaccion.commit();
		log.info("Numero de telefono del cliente 'Paco' ha cambiado a " + numNuevoPaco);
		manager.close();
	}

	/**
	 * Metodo que da de alta un cliente en la Base de Datos
	 * 
	 * @param paco
	 * @param detallesPaco
	 * @param manager
	 * @param transaccion
	 */
	private static void altaCliente(Cliente paco, DetallesCliente detallesPaco, EntityManager manager,
			EntityTransaction transaccion) {
		transaccion.begin();

		detallesPaco.setCliente(paco);

		manager.persist(paco);

		transaccion.commit();
		log.info("Cliente 'Paco' persistido con exito");
		manager.close();
	}

}
