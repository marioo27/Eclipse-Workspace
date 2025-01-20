package es.aad;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.city.ies.RequisitosExamen2425;

/**
 * Esta es la clase donde se gestiona la Base de Dato al igual que todos sus metodos que
 * posteriormente seran invocados en la clase Programa
 * 
 * @author Mario Garcia
 * @since 18-12-2024
 * @version 1.0.0
 */
public class DbManager implements RequisitosExamen2425 {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbManager.class);

	private Connection conexion = null;

	private Properties fichPropiedades = new Properties();

	private String DRIVER = "driver";
	private String URL = "url";
	private String USUARIO = "user";
	private String CONTRASENIA = "password";

	/**
	 * Este es el unico constructor, un constructor sin parametros que inicializa la conexion con la
	 * Base de Datos
	 */
	public DbManager() {

		try {
			fichPropiedades.load(new FileInputStream("cliente.properties"));
			Class.forName(fichPropiedades.getProperty(DRIVER));
			conexion = DriverManager.getConnection(fichPropiedades.getProperty(URL),
					fichPropiedades.getProperty(USUARIO), fichPropiedades.getProperty(CONTRASENIA));
			LOGGER.debug("Conexion establecida");
		} catch (ClassNotFoundException | SQLException | IOException e) {
			LOGGER.error("Error registrando el Driver y el fichero de propiedades [" + e.getMessage() + "]");
		}
	}

	/**
	 * Este metodo no recibe parametros, comprueba si hay una conexion abierta y si es asi, la cierra
	 */
	public void cerrarDb() {
		if (conexion != null)
			try {
				conexion.close();
				LOGGER.debug("Conexion cerrada con exito");
			} catch (SQLException e) {
				LOGGER.error("Error al cerrar la conexion [" + e.getMessage() + "]");
			}
	}

	/**
	 * Este método invoca la función de BD 'TotalVentasPorCliente' para obtener el importe total de
	 * ventas de un cliente pasado como parámetro
	 * 
	 * @param idCliente identificador del cliente
	 * @return total de ventas del clientes pasado como parámetro
	 */
	@Override
	public double getTotalVentas(int idCliente) { // He cambiado el tipo de dato que devuelve este metodo (de int a
													// double) ya que la funcion almacenada en la Base de Datos que se
													// invoca devuelve un numero decimal, no un entero

		double totalVentas = 0;

		CallableStatement csGetTotalVentas = null;

		if (conexion != null) {
			try {
				csGetTotalVentas = conexion.prepareCall(SQL.GET_TOTAL_VENTAS);

				csGetTotalVentas.registerOutParameter(1, Types.DOUBLE);
				csGetTotalVentas.setInt(2, idCliente);

				csGetTotalVentas.execute();

				totalVentas = csGetTotalVentas.getDouble(1);

				LOGGER.trace("El total de ventas del cliente " + idCliente + " es " + totalVentas);

			} catch (SQLException e) {
				LOGGER.error("Error al ejecutar el CallableStatement [" + e.getMessage() + "]");
			} finally {

				if (csGetTotalVentas != null)
					try {
						csGetTotalVentas.close();
					} catch (SQLException e) {
						LOGGER.error("Error al cerrar el CallableStatement [" + e.getMessage() + "]");
					}

			}
		} else
			LOGGER.error("Conexion no inicializada");

		return totalVentas;
	}

	/**
	 * Este método invoca el procedimiento almacenado de BD 'DecrementarStock', el cual decrementa en
	 * una unidad el estocage de un producto pasado como parámetro
	 * 
	 * @param idProducto identificador del producto
	 */
	@Override
	public boolean reducirStockProductos(int idProducto) {
		boolean exito = false;

		CallableStatement csReducirStock = null;

		if (conexion != null) {
			try {
				csReducirStock = conexion.prepareCall(SQL.REDUCIR_STOCK);

				csReducirStock.setInt(1, idProducto);
				csReducirStock.execute();

				exito = true;
				LOGGER.trace("Stock del producto " + idProducto + " reducido");

			} catch (SQLException e) {
				LOGGER.error("Error al ejecutar el CallableStatement [" + e.getMessage() + "]");
			} finally {
				if (csReducirStock != null)
					try {
						csReducirStock.close();
					} catch (SQLException e) {
						LOGGER.error("Error al cerrar el CallableStatement [" + e.getMessage() + "]");
					}
			}

		} else
			LOGGER.error("Conexion no inicializada");

		return exito;
	}

	/**
	 * Este método añade un determinado producto a la BD
	 * 
	 * @param nomProducto identificador del producto
	 * @param precio      precio unitario del producto
	 * @param stock       cantidad disponible del producto
	 */
	@Override
	public boolean aniadirProducto(String nomProducto, double precio, int stock) {

		boolean exito = false;

		PreparedStatement psAniadirProducto = null;

		if (conexion != null) {
			try {
				psAniadirProducto = conexion.prepareStatement(SQL.ANIADIR_PRODUCTO);

				psAniadirProducto.setString(1, nomProducto);
				psAniadirProducto.setDouble(2, precio);
				psAniadirProducto.setInt(3, stock);

				psAniadirProducto.executeUpdate();
				exito = true;

			} catch (SQLException e) {
				LOGGER.error("Error al ejecutar el PreparedStatement [" + e.getMessage() + "]");
			} finally {
				if (psAniadirProducto != null)
					try {
						psAniadirProducto.close();
					} catch (SQLException e) {
						LOGGER.error("Error al cerrar el PreparedStatement [" + e.getMessage() + "]");
					}
			}

		} else
			LOGGER.error("Conexion no inicializada");

		return exito;
	}

	/*
	 * @Override public void actualizarTotales() { // TODO Auto-generated method stub} He comentado este
	 * metodo siguiendo las instrucciones proporcionadas, he decidido realizar el ejercicio 4 y no el 3
	 */

	/**
	 * Este metodo no recibe parametros; su funcion es desactivar el Autocommit de la conexion desde
	 * fuera de esta clase
	 */
	public void setAutocommitFalse() {

		if (conexion != null) {
			try {
				conexion.setAutoCommit(false);
			} catch (SQLException e) {
				LOGGER.error("Error al desactivar el autocommit [" + e.getMessage() + "]");
			}
		} else
			LOGGER.error("Conexion no inicializada");
	}

	/**
	 * Este metodo no recibe parametros; su funcion es activar el Autocommit de la conexion desde fuera
	 * de esta clase
	 */
	public void setAutocommitTrue() {

		if (conexion != null) {
			try {
				conexion.setAutoCommit(true);
			} catch (SQLException e) {
				LOGGER.error("Error al activar el autocommit [" + e.getMessage() + "]");
			}
		} else
			LOGGER.error("Conexion no inicializada");
	}

	/**
	 * Este metodo no recibe parametros; su funcion es realizar un Commit de la conexion desde fuera de
	 * esta clase
	 */
	public void comit() {
		if (conexion != null) {
			try {
				conexion.commit();
			} catch (SQLException e) {
				LOGGER.error("Error al hacer el commit [" + e.getMessage() + "]");
			}
		} else
			LOGGER.error("Conexion no inicializada");
	}

	/**
	 * Este metodo no recibe parametros; su funcion es realizar un Rollback de la conexion desde fuera
	 * de esta clase
	 */
	public void rolback() {
		if (conexion != null) {
			try {
				conexion.rollback();
			} catch (SQLException e) {
				LOGGER.error("Error al hacer rollback [" + e.getMessage() + "]");
			}
		} else
			LOGGER.error("Conexion no inicializada");

	}

	/**
	 * Este metodo recibe por parametro un identificador de un cliente y vuelca al log la informacin de
	 * dicho cliente
	 * 
	 * @param idCliente identificador del cliente
	 */
	public void mostrarCliente(int idCliente) {

		PreparedStatement psMostrarCliente = null;
		ResultSet rsResultSet = null;

		if (conexion != null) {
			try {
				psMostrarCliente = conexion.prepareStatement(SQL.MOSTRAR_CLIENTE);

				psMostrarCliente.setInt(1, idCliente);

				rsResultSet = psMostrarCliente.executeQuery();

				LOGGER.debug(rsResultSet.getString(1));

			} catch (SQLException e) {
				LOGGER.error("Error al ejecutar el PreparedStatement [" + e.getMessage() + "]");
			} finally {
				if (psMostrarCliente != null)
					try {
						psMostrarCliente.close();
					} catch (SQLException e) {
						LOGGER.error("Error al cerrar el PreparedStatement [" + e.getMessage() + "]");
					}
			}

		} else
			LOGGER.error("Conexion no inicializada");
	}

}
