package es.aad;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.city.ies.RequisitosPractica2;

public class DbManager implements RequisitosPractica2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbManager.class);

	private Connection conexion = null;

	private final String FICHERO_PROPIEDADES = "BD_MarioGarcia.properties";
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

		} catch (FileNotFoundException e) {
			LOGGER.error("Error cargando el fichero de propiedades [" + e.getMessage() + "]");
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.error("Error cargando el fichero de propiedades [" + e.getMessage() + "]");
		} catch (ClassNotFoundException e) {
			LOGGER.error(
					"Error registrando el driver: " + propiedades.getProperty(DRIVER) + "[" + e.getMessage() + "]");
		} catch (SQLException e) {
			LOGGER.error("Error estableciendo conexion con la base de datos [" + e.getMessage() + "]");
		}
	}

	public void cerrarDB() {
		if (conexion != null)
			try {
				conexion.close();
				LOGGER.debug("Se ha cerrado correctamente la conexion con la BDD");
			} catch (SQLException e) {
				LOGGER.error("Error al cerrar la conexion con la BDD [" + e.getMessage() + "]");
			}
		else
			LOGGER.debug("La conexion con la BDD es nula, no se puede cerrar");
	}

	@Override
	public String getRegalo(String emailPersona) {

		CallableStatement csGetRegalo;
		String regalo = "";

		if (conexion != null) {
			try {
				csGetRegalo = conexion.prepareCall(SQL.GET_REGALO_POR_EMAIL);
				csGetRegalo.registerOutParameter(1, Types.VARCHAR);
				csGetRegalo.setString(2, emailPersona);

				csGetRegalo.execute();

				regalo = csGetRegalo.getString(1);

			} catch (SQLException e) {
				LOGGER.error("Error al ejecutar el CallableStatement [" + e.getMessage() + "]");
			}

		} else
			LOGGER.error("La conexion es nula, no se pudo dar de alta una persona");

		if (regalo.equals(""))
			LOGGER.warn("El email " + emailPersona + " no tiene regalos :(");
		else
			LOGGER.debug("El email " + emailPersona + " tiene un regalo!!! -> " + regalo);

		return regalo;
	}

	@Override
	public boolean aniadirPersona(String nomPersona, String emailPersona, LocalDate fechaNac) {

		String fechaNacimiento = fechaNac.toString();

		boolean exito = false;
		PreparedStatement psAniadirPersona = null;

		if (conexion != null) {
			try {
				psAniadirPersona = conexion.prepareStatement(SQL.ANIADIR_PERSONA);

				psAniadirPersona.setString(1, nomPersona);
				psAniadirPersona.setString(2, emailPersona);
				psAniadirPersona.setString(3, fechaNacimiento);

				psAniadirPersona.executeUpdate();

				LOGGER.debug("Persona " + nomPersona + " dada de alta con exito");
				exito = true;

			} catch (SQLException e) {
				LOGGER.error("Error durante la inserccion de personas");
			} finally {
				if (psAniadirPersona != null)
					try {
						psAniadirPersona.close();
					} catch (SQLException e) {
						LOGGER.error("Error al cerrar el PreparedStatement [" + e.getMessage() + "]");
					}
			}
		} else
			LOGGER.error("La conexion es nula, no se pudo dar de alta una persona");

		return exito;
	}

	@Override
	public void actualizarTotales() {
		if (conexion != null) {
			PreparedStatement psActualizarTotales = null;

			PersonaEvento p1 = new PersonaEvento(3, 3, true, 6);
			PersonaEvento p2 = new PersonaEvento(18, 2, true, 10);
			PersonaEvento p3 = new PersonaEvento(19, 2, true, 9);

			List<PersonaEvento> datosActualizados = new ArrayList<PersonaEvento>();
			datosActualizados.add(p1);
			datosActualizados.add(p2);
			datosActualizados.add(p3);

			try {
				psActualizarTotales = conexion.prepareStatement(SQL.ACTUALIZAR_TOTALES);
				conexion.setAutoCommit(false);

				for (PersonaEvento p : datosActualizados) {
					psActualizarTotales.setBoolean(1, p.isAsistio());
					psActualizarTotales.setInt(2, p.getNota());
					psActualizarTotales.setInt(3, p.getPersonaId());
					psActualizarTotales.setInt(4, p.getEventoId());
				}

				psActualizarTotales.executeUpdate();
				psActualizarTotales.clearParameters();

				conexion.commit();
				LOGGER.debug("Datos actualizados correctamente");
			} catch (SQLException e) {
				LOGGER.error("Error durante la actualizacion de los datos [" + e.getMessage() + "]");
				try {
					conexion.rollback();
					LOGGER.debug("Realizando Rollback");
				} catch (SQLException e1) {
					LOGGER.error("Error al realizar el Rollback [" + e1.getMessage() + "]");
				}
			} finally {
				if (psActualizarTotales != null) {
					try {
						conexion.setAutoCommit(true);
						psActualizarTotales.close();
					} catch (SQLException e) {
						LOGGER.error("Error al cerrar el PreparedStatement [" + e.getMessage() + "]");
					}
				}
			}

		} else
			LOGGER.error("La conexion es nula, no se pudo dar de alta una persona");

	}

	public void desactivarAutocomit() {
		if (conexion != null)
			try {
				conexion.setAutoCommit(false);
			} catch (SQLException e) {
				LOGGER.error("Error al desactivar el autocommit [" + e.getMessage() + "]");
			}
		else
			LOGGER.error("Conexion no inicializada");
	}
	
	public void activarAutocomit() {
		if (conexion != null)
			try {
				conexion.setAutoCommit(true);
			} catch (SQLException e) {
				LOGGER.error("Error al activar el autocommit [" + e.getMessage() + "]");
			}
		else
			LOGGER.error("Conexion no inicializada");
	}
	
	public void rolback() {
		if (conexion != null)
			try {
				conexion.rollback();
			} catch (SQLException e) {
				LOGGER.error("Error al ejecutar el rollback [" + e.getMessage() + "]");
			}
		else
			LOGGER.error("Conexion no inicializada");
	}

	public void comit() {
		if (conexion != null)
			try {
				conexion.commit();
			} catch (SQLException e) {
				LOGGER.error("Error al ejecutar el commit [" + e.getMessage() + "]");
			}
		else
			LOGGER.error("Conexion no inicializada");
	}
}
