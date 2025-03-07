package es.MarioGarcia;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class Programa {

	private static Logger LOG = LoggerFactory.getLogger(Programa.class);
	private static final EntityManagerFactory factoria = Persistence.createEntityManagerFactory("persistence");

	public static void main(String[] args) {

		EntityManager manager = null;
		EntityTransaction transaccion = null;

		try {
			manager = factoria.createEntityManager();

//TRANSACCION 1: ALTA DE ALUMNOS Y SUS DATOS DE CONTACTO
			transaccion = manager.getTransaction();

			transaccion.begin();

			Alumno dario = new Alumno();
			Alumno fermin = new Alumno();

			Contacto contactDario = new Contacto();
			Contacto contactFermin = new Contacto();

			int numExpDario = 101;
			int numExpFermin = 102;

			if (manager.find(Alumno.class, numExpDario) != null) {
				dario.setId(101);
				dario.setNomApe("Dario Pazos");
				dario.setFechaNac(LocalDate.of(2005, 6, 15));

				contactDario.setEmail("dario.pazos@instituto.es");
				contactDario.setTelefono("912443213");

				contactDario.setAlum(dario);
				dario.setContact(contactDario);

				manager.persist(dario);
				LOG.info("Alumno 'Dario' dado de alta la BDD con exito [" + dario.toString() + "]");
			}

			if (manager.find(Alumno.class, numExpFermin) != null) {
				dario.setId(102);
				dario.setNomApe("Fermin Sanz");
				dario.setFechaNac(LocalDate.of(2002, 9, 21));

				contactDario.setEmail("fermin.sanz@instituto.es");
				contactDario.setTelefono("6660006666");

				contactDario.setAlum(fermin);
				dario.setContact(contactFermin);

				manager.persist(fermin);
				LOG.info("Alumno 'Dario' dado de alta en la BDD con exito [" + fermin.toString() + "]");
			}

			transaccion.commit();

//TRANSACCION 2: CONSULTA Y MODIFICACION DE UNA EMPRESA (SIN HQL)

			transaccion = manager.getTransaction();

			transaccion.begin();

			Empresa emId1 = manager.find(Empresa.class, 1);

			if (emId1 != null) {
				LOG.info("Se ha reperado la empresa '" + emId1.getNombre() + "\n" + emId1.toString());

				String antiguaDir = emId1.getDireccion();

				emId1.setDireccion("Carretera de Colmenar Km 12,800");

				manager.persist(emId1);

				LOG.info("Se ha cambiado la direccion de la empresa " + emId1.getNombre() + " de:  " + antiguaDir
						+ " a: " + emId1.getDireccion() + "\n" + emId1.toString());
			}

			transaccion.commit();

//TRANSACCION 3: LISTAR EMPRESAS Y SUS TUTORES A EXCEPCION DE LOS TUTORES DE INSTITUTO (CON HQL) 

			transaccion = manager.getTransaction();

			transaccion.begin();

			Query query = manager.createQuery("FROM tutores WHERE  tipo = :tipo");  // Doy por hecho que al solo haber definidos dos valores con un enumerado en el campo
																					//'tipo' ('Instituto', 'Empresa'), para mostrar los que NO son de tipo 'Instituto', 
																					// basta con mostrar los que son de tipo 'Empresa'

			query.setParameter(0, "empresa");

			List<Tutor> listaTutoresEmpresa = query.getResultList();

			for (Tutor t : listaTutoresEmpresa) {
				if (t.getEm() != null)
					LOG.info("Tutores de empresa recuperados\n Nombre de la Empresa:" + t.getEm().getNombre()
							+ " Nombre del tutor: " + t.getNombre() + " Email de la Empresa" + t.getEm().getEmail());
			}

			transaccion.commit();

		} catch (IllegalStateException e) {
			LOG.error("Error durante la transaccion y la persitencia de datos [" + e.getMessage() + "]");
		} catch (Exception e) {
			LOG.error("Error durante la transaccion y la persitencia de datos [" + e.getMessage() + "]");
		} finally {
			if (manager != null && manager.isOpen())
				manager.close();
			if (transaccion != null && transaccion.isActive())
				transaccion.rollback();
		}

	}

}
