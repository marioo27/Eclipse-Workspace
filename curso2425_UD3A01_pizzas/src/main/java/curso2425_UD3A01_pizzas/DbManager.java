package curso2425_UD3A01_pizzas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.log.Log;

public class DbManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbManager.class);

	private Connection conexion = null;

	private final String FICHERO_PROPIEDADES = "pizzas.properties";
	private final String DRIVER = "driver";
	private final String URL = "url";
	private final String USUARIO = "user";
	private final String CONTRASENA = "password";

	private String CODIGO = "cod_pizza";
	private String NOMBRE = "nombre_pizza";
	private String PRECIO = "precio";

	public DbManager() {

		Properties propiedades = new Properties();

		try {
			propiedades.load(new FileInputStream(FICHERO_PROPIEDADES));

			Class.forName(propiedades.getProperty(DRIVER));
			LOGGER.debug("Se ha registrado correctamente el driver [" + propiedades.getProperty(DRIVER) + "]");

			conexion = DriverManager.getConnection(propiedades.getProperty(URL), propiedades.getProperty(USUARIO),
					propiedades.getProperty(CONTRASENA));
			LOGGER.debug("Se ha creado la conexion correctamente");

		} catch (IOException | ClassNotFoundException | SQLException e) {
			LOGGER.error("ERROR " + e.getMessage());
		}

	}

	public void cerrarDb() {

		if (conexion != null) {
			try {
				conexion.close();
				LOGGER.debug("Se ha cerrado correctamente la Base de Datos");

			} catch (SQLException e) {
				LOGGER.error("ERROR al cerrar la base de datos" + e.getMessage());
			}
		}
	}

	public void mostrarIngredientes(String nombrePizza) {

		PreparedStatement pstIngredientes = null;
		ResultSet rstIngredientes = null;

		if (conexion != null) {
			try {
				pstIngredientes = conexion.prepareStatement(SQL.INGREDIENTES_PIZZA);
				pstIngredientes.setString(1, nombrePizza);

				rstIngredientes = pstIngredientes.executeQuery();

				if (rstIngredientes.next())
					LOGGER.debug(rstIngredientes.getString(1));
				else
					LOGGER.warn("No se ha encontrado nada");

			} catch (SQLException e) {
				LOGGER.error("Error " + e.getMessage());
			}

		} else
			LOGGER.error("Conexion no inicializada");

	}

	public float getPrecioPizza(int codPizza) {

		CallableStatement csPrecioPizza = null;
		float precio = 0;

		if (conexion != null) {

			try {
				csPrecioPizza = conexion.prepareCall(SQL.GET_PRECIO);
				// Dado que se trata de una funcion debe devolver un valor, por lo que va a devolver un valor de
				// salida que hay que registrar
				csPrecioPizza.registerOutParameter(1, Types.FLOAT);
				csPrecioPizza.setFloat(2, codPizza);

				LOGGER.debug(
						"Antes de ejecutar el procedimiento: " + SQL.GET_PRECIO + " con codigo de pizza: " + codPizza);
				csPrecioPizza.executeUpdate();

				LOGGER.debug("Se ha ejecutado correctamente el procedimiento");

				precio = csPrecioPizza.getFloat(1);

			} catch (SQLException e) {
				LOGGER.error("Error " + e.getMessage());
			} finally {
				if (csPrecioPizza != null) {
					try {
						csPrecioPizza.close();
					} catch (SQLException e) {
						LOGGER.error("Error " + e.getMessage());
					}
				}
			}

		} else
			LOGGER.error("Conexion no inicializada");

		return precio;
	}

	public void addIngrediente(String ingrediente, String nombrePizza, float cant) {

		CallableStatement csAddIngrediente = null;

		if (conexion != null) {
			try {
				csAddIngrediente = conexion.prepareCall(SQL.ADD_INGREDIENTE);

				csAddIngrediente.setString(1, ingrediente);
				csAddIngrediente.setString(2, nombrePizza);
				csAddIngrediente.setFloat(3, cant);

				LOGGER.debug("Antes de ejecutar el procedimiento: " + SQL.ADD_INGREDIENTE + " con ingrediente "
						+ ingrediente + " pizza " + nombrePizza + " y cantidad " + cant);
				csAddIngrediente.executeUpdate();

				LOGGER.debug("Se ha ejecutado correctamente el procedimiento");

			} catch (SQLException e) {
				LOGGER.error("Error durante la consulta " + e.getMessage());
			} finally {
				if (csAddIngrediente != null) {
					try {
						csAddIngrediente.close();
					} catch (SQLException e) {
						LOGGER.error("Error durante el cierre del Callable Statement " + e.getMessage());
					}
				}
			}

		} else
			LOGGER.error("Conexion no inicializada");
	}

	public void altaPizzaTransaccional() {// falta añadir el ingrediente si existe

		PreparedStatement pstAltaPizza = null;

		Map<Ingrediente, Double> ingredientes = new HashMap<Ingrediente, Double>();

		Pizza p = new Pizza("Melanzana", 16, ingredientes);
		Ingrediente mozzarella = new Ingrediente("Mozzarella");
		Ingrediente tomate = new Ingrediente("Tomate ");
		Ingrediente berenjena = new Ingrediente("Berenjena ");
		Ingrediente aceiteBalsamico = new Ingrediente("Aceite balsámico");

		if (conexion != null) {
			if (!pizzaExists(p.getNombre())) {

				try {
					pstAltaPizza = conexion.prepareStatement(SQL.ADD_PIZZA);
					conexion.setAutoCommit(false);

					pstAltaPizza.setString(1, p.getNombre());
					pstAltaPizza.setFloat(2, p.getPrecio());

					pstAltaPizza.executeUpdate();

					if (!ingredienteExists(mozzarella.getNombre()))
						addIngrediente(mozzarella.getNombre(), p.getNombre(), 425);
					else {
						
					}
					if (!ingredienteExists(tomate.getNombre()))
						addIngrediente(tomate.getNombre(), p.getNombre(), 245);
					
					if (!ingredienteExists(berenjena.getNombre()))
						addIngrediente(berenjena.getNombre(), p.getNombre(), 600);
					if (!ingredienteExists(aceiteBalsamico.getNombre()))
						addIngrediente(aceiteBalsamico.getNombre(), p.getNombre(), 90);

				} catch (SQLException e) {
					LOGGER.error("Error durante la inserccion de la pizza " + e.getMessage());
				}

			} else
				LOGGER.error("Error, conexion no inicializada");
		} else
			LOGGER.warn("La pizza con nombre " + p.getNombre() + " ya existe");

	}

	public String ingredienteExistente(String nombreIngrediente) {

		PreparedStatement pstNuevoIngrediente = null;

		if (conexion != null) {

			try {
				pstNuevoIngrediente = conexion.prepareStatement(SQL.INSERT_INGREDIENTE);

				pstNuevoIngrediente.setString(1, nombreIngrediente);

				pstNuevoIngrediente.executeQuery();

				if (pstNuevoIngrediente != null)
					pstNuevoIngrediente.close();

			} catch (SQLException e) {
				LOGGER.error("Error durante la insercion del ingrediente" + e.getMessage());
			}

		} else
			LOGGER.error("Error, la conexion no esta inicializada");
		
		return nombreIngrediente;

	}

	public boolean pizzaExists(String nombrePizza) {

		boolean existe = false;
		PreparedStatement pstExistePizza = null;
		ResultSet rstExistePizza = null;

		try {
			pstExistePizza = conexion.prepareStatement(SQL.PIZZA_EXISTS);

			pstExistePizza.setString(1, nombrePizza);
			rstExistePizza = pstExistePizza.executeQuery();

			if (rstExistePizza.next()) {
				existe = true;
				LOGGER.debug("La pizza " + nombrePizza + " ya existe");
			} else {
				existe = false;
				LOGGER.debug("La pizza " + nombrePizza + " no existe");
			}

		} catch (SQLException e) {
			LOGGER.error("Error en la ejecucion del Prepared Statement " + e.getMessage());
		} finally {
			try {
				if (rstExistePizza != null)
					rstExistePizza.close();

				if (pstExistePizza != null)
					pstExistePizza.close();
			} catch (SQLException e) {
				LOGGER.error("Error al cerrar el ResultSet o Statement [" + e.getMessage() + "]");
			}
		}

		return existe;
	}

	public boolean ingredienteExists(String nombreIngrediente) {

		boolean existe = false;
		PreparedStatement pstExisteIngrediente = null;
		ResultSet rstExisteIngrediente = null;

		try {
			pstExisteIngrediente = conexion.prepareStatement(SQL.PIZZA_EXISTS);

			pstExisteIngrediente.setString(1, nombreIngrediente);
			rstExisteIngrediente = pstExisteIngrediente.executeQuery();

			if (rstExisteIngrediente.next()) {
				existe = true;
				LOGGER.debug("El ingrediente " + nombreIngrediente + " ya existe");
			} else {
				existe = false;
				LOGGER.debug("El ingrediente " + nombreIngrediente + " no existe");
			}

		} catch (SQLException e) {
			LOGGER.error("Error en la ejecucion del Prepared Statement " + e.getMessage());
		} finally {
			try {
				if (rstExisteIngrediente != null)
					rstExisteIngrediente.close();

				if (pstExisteIngrediente != null)
					pstExisteIngrediente.close();
			} catch (SQLException e) {
				LOGGER.error("Error al cerrar el ResultSet o Statement [" + e.getMessage() + "]");
			}
		}

		return existe;
	}

	// Pizza -> nombre, precio, mapa <ingrediente, double>

	/*
	 * altaNuevaPizza(Pizza p) select si existe -> log.warn (existe)
	 * 
	 * no existe -> insert tabla pizza existe cada ingrediente? si, nada; no, dar de alta
	 * 
	 * insert pizza ingrediente para relacionarlo
	 * 
	 * rollback
	 */

}
