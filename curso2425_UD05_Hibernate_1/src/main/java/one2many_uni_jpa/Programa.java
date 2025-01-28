package one2many_uni_jpa;

import java.time.LocalDate;
import java.util.Iterator;

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
	
	public static void main(String[] args) {
		Director director = new Director();
		director.setCodigo_director(10);
		director.setNombre_director("Stevan Seagal");
		
		Pelicula peli1 = new Pelicula("Duro de matar I", LocalDate.of(1995, 5, 23));
		Pelicula peli2 = new Pelicula("Duro de matar II", LocalDate.of(1996, 5, 23));
		Pelicula peli3 = new Pelicula("Duro de matar III", LocalDate.of(1997, 5, 23));
		
		EntityManager manager = null;
		EntityTransaction transaction = null;
		try {
			manager = factoria.createEntityManager();
			transaction = manager.getTransaction();
			
			transaction.begin();
			
			manager.persist(director);
			
			/*
			manager.persist(peli1);
			manager.persist(peli2);
			manager.persist(peli3);
			*/
			
			transaction.commit();
			
			manager.close();
			
			manager = factoria.createEntityManager();
			
			//Con esto verificamos el comportamiento del fetch: recuperamos las peliculas de primeras o solo al acceder a ellas
			Director dirBuscado = manager.find(Director.class, 10);
			
			if(dirBuscado != null) {
				log.info("Director encontrado: {}", dirBuscado.getNombre_director());
				for (Pelicula p : dirBuscado.getPelis_director()) {
					log.info("Pelicula: {}",p.getTitulo_pelicula());
				}
				
				transaction = manager.getTransaction();
				transaction.begin();
				manager.remove(dirBuscado);
				transaction.commit();
				
			}
			
			manager.close();
			
			// A partir de aqui se evidencia la bidireccionalidad. Es decir, a partir de una peli, puedo conocer su director
			manager = factoria.createEntityManager();
			
			Pelicula peliBuscada = manager.find(Pelicula.class, 6);
			if(peliBuscada != null) {
				log.info("Pelicula encontrada: {}", peliBuscada);
			}
			
			manager.close();
			
		} catch (IllegalStateException e) {
			log.error("Error durante la gestion del contexto de persistencia");
		} catch (PersistenceException e) {
			log.error("Error durante la persistencia de objetos");
			if(transaction.isActive())
				transaction.rollback();
		} finally {
			if(manager != null && manager.isOpen())
				manager.close();
			if(factoria.isOpen())
				factoria.close();
		}
	}
}
