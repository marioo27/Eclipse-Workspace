package es.aad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta es la clase principal, desde la que se ejecutaran los metodos creados en la clase DbManager,
 * en esta clase no se ha definido ningun metodo. Aparte de la logica solicitada, tambien se prueba
 * la ejecucin de algunos metodos
 * 
 * @author Mario Garcia
 * @since 18-12-2024
 * @version 1.0.0
 */
public class Programa {
	private static final Logger LOGGER = LoggerFactory.getLogger(Programa.class);

	public static void main(String[] args) {

		DbManager db = new DbManager();
		/*
		 * Ya que el metodo aniadirProducto solo aniade un producto, he creado varios metodos que se invocan
		 * aqui para activar o desactivar el Autocommit de la Base de Datos, hacer un Commit y un Rollback
		 * desde esta clase. De esta manera, soy capaz de insertar los productos de forma transaccional como
		 * solicitado.
		 * 
		 */
		db.setAutocommitFalse();

		boolean exitoProducto1 = db.aniadirProducto("Teclado IBM", 12.30, 10);
		boolean exitoProducto2 = db.aniadirProducto("Ratón IBM", 6.75, 25);
		boolean exitoProducto3 = db.aniadirProducto("Trackpad IBM", 27.95, 5);

		if (exitoProducto1 && exitoProducto2 && exitoProducto3) {
			LOGGER.debug("Productos dados de alta transaccionalmente con exito");
			db.comit();
			LOGGER.trace("Commit realizado, los productos han sido dados de alta");
		} else {
			LOGGER.error("Error al dar de alta los productos dados, se hace rollback");
			db.rolback();
			LOGGER.warn("Rollback realizado, los productos no han sido dados de alta");
		}

		db.setAutocommitTrue();

		db.getTotalVentas(1);

		/**
		 * para actualizar el stock de los productos, se usa la misma logica que para insertar nuevos
		 * productos, se invoca al metodo que reduce uno y se gestiona con los metodos de
		 * setAutocommitFalse, setAutocommitTrue, comit y rolback para hacerlo de forma transaccional
		 */
		db.setAutocommitFalse();

		boolean exitoReducirStock1 = db.reducirStockProductos(1);
		boolean exitoReducirStock2 = db.reducirStockProductos(1);
		boolean exitoReducirStock3 = db.reducirStockProductos(2);

		if (exitoReducirStock1 && exitoReducirStock2 && exitoReducirStock3) {
			LOGGER.debug("Stock de productos actualizado transaccionalmente con exito");
			db.comit();
			LOGGER.trace("Commit realizado, el stock de los productos dados ha sido reducido");
		} else {
			LOGGER.error("Error al dar reducir el stock de los productos dados, se hace rollback");
			db.rolback();
			LOGGER.warn("Rollback realizado, el stock de los productos no han sido actualizado");
		}

		db.setAutocommitTrue();

		db.mostrarCliente(1);

		db.cerrarDb();
	}

}
