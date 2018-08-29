package hu.dojo.backend;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import hu.dojo.jpa.AbstractEntity;

@Stateless
public class Adder {	
	
	public void addEntity(AbstractEntity entity) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("dojo-jpa");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();
	}	
}


