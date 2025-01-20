package es.ciudadescolar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DbManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbManager.class);

	private Connection conexion = null;

	private final String FICHERO_PROPIEDADES = "DAM2_DB.properties";
	private final String DRIVER = "driver";
	private final String URL = "url";
	private final String USUARIO = "user";
	private final String CONTRASENA = "password";

	public DbManager() {

		Properties propiedades = new Properties();

		try {
			propiedades.load(new FileInputStream(FICHERO_PROPIEDADES));

			Class.forName(propiedades.getProperty(DRIVER));
			LOGGER.debug("Se ha registrado correctamente el driver: " + propiedades.getProperty(DRIVER));
			conexion = DriverManager.getConnection(propiedades.getProperty(URL), propiedades.getProperty(USUARIO),
					propiedades.getProperty(CONTRASENA));
			LOGGER.debug("Se ha creado la conexion correctamente");

		} catch (ClassNotFoundException e) {
			LOGGER.error("Error registrando el driver: " + propiedades.getProperty(DRIVER));
		} catch (SQLException e) {
			LOGGER.error("Error estableciendo conexion con la base de datos: " + e.getMessage());
		} catch (FileNotFoundException e) {
			LOGGER.error("Error cargando el fichero properties " + FICHERO_PROPIEDADES);
		} catch (IOException e) {
			LOGGER.error("Error cargando el fichero " + FICHERO_PROPIEDADES);
		}
	}

	public void cerrarDb() {
		if (conexion != null) {
			try {
				conexion.close();
				LOGGER.debug("Se ha cerrado correctamente la base de datos");
			} catch (SQLException e) {

				LOGGER.error("Error al cerrar la base de datos");
			}
		}
	}

	public void addPeliculaTransaccional(List<Pelicula> listaPeliculas) {

		PreparedStatement pstAddPelicula = null;

		if (conexion != null) {
			try {
				pstAddPelicula = conexion.prepareStatement(SQL.ADD_PELICULA);
				conexion.setAutoCommit(false);

				for (Pelicula p : listaPeliculas) {
					pstAddPelicula.setString(1, p.getNombre());
					pstAddPelicula.setInt(2, p.getAnio());

					pstAddPelicula.executeUpdate();
				}
				conexion.commit();

			} catch (SQLException e) {
				LOGGER.error("Error durante la inserccion transaccional de peliculas [" + e.getMessage() + "]");
			} finally {
				if (pstAddPelicula != null) {
					try {
						pstAddPelicula.close();
						conexion.setAutoCommit(true);
					} catch (SQLException e) {
						LOGGER.error("Error al cerrar el Prepared Statement [" + e.getMessage() + "]");
					}

				}
			}

		} else
			LOGGER.error("Conexion no inicializada");

	}

	public int getNumActores(String nombrePelicula) {

		CallableStatement csNumActores = null;
		int numActores = 0;
		
		if(conexion != null) {
			try {
				csNumActores = conexion.prepareCall(SQL.GET_NUM_ACTORES);
				
				csNumActores.registerOutParameter(1, Types.INTEGER);
				csNumActores.setString(2, nombrePelicula);
				
				LOGGER.debug("Antes de ejecutar la funcion fun_GetNumActores");
				csNumActores.execute();
				LOGGER.debug("Se ha ejecutado correctamente la funcion");
				
				numActores = csNumActores.getInt(1);
				
			} catch (SQLException e) {
				LOGGER.error("Error durante la consulta [" + e.getMessage() + "]");

			}
			
		}else
			LOGGER.error("Conexion no inicializada");

		return numActores;

	}
	
	public void getPelisProta(String nombre, String apellido) {
		
		PreparedStatement pstProtas = null;
		ResultSet rstProtas = null;

		if (conexion != null) {
			try {
				pstProtas = conexion.prepareStatement(SQL.IS_PROTAGONIST);
				pstProtas.setString(1, nombre);
				pstProtas.setString(2, apellido);

				rstProtas = pstProtas.executeQuery();

				if (rstProtas.next()) {
					LOGGER.debug(rstProtas.getString(1));
					pstProtas.setString(2, apellido);
				}
				else
					LOGGER.warn("No se ha encontrado nada");

			} catch (SQLException e) {
				LOGGER.error("Error " + e.getMessage());
			}

		} else
			LOGGER.error("Conexion no inicializada");

	}

}
