package curso2425_UD5_Hibernate_nativo;

import java.time.LocalDate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Programa {
	private static final Logger LOG = LoggerFactory.getLogger(Programa.class);

	public static void main(String[] args) {

		SessionFactory factoria = null; // analogo a EntityManagerFactory en JPA
		Session sesion = null; // analogo a EntityManager en JPA
		Transaction transaccion = null; // analogo a EntityTransaction en JPA

		Director director = new Director();
		director.setCodigo_director(103);
		director.setNombre_director("Chris Wedge");

		Pelicula pelicula = new Pelicula("Ice Age", LocalDate.of(2000, 8, 30));

		pelicula.setDir_pelicula(director);

		try {
			factoria = new Configuration().configure().buildSessionFactory();
			sesion = factoria.openSession();

			transaccion = sesion.beginTransaction();

			sesion.persist(pelicula);

			transaccion.commit();

		} catch (HibernateException e) {
			LOG.error("Error durante la gestiond e persistencia usando API nativa [" + e.getMessage() + "]");

			if (transaccion != null)
				transaccion.rollback();
			
		} finally {

			if ((sesion != null) && (sesion.isOpen()))
				sesion.close();

			if ((factoria != null) && !(factoria.isClosed()))
				factoria.close();
		}
	}

}
