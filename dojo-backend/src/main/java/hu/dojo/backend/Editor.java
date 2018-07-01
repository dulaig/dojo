package hu.dojo.backend;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import hu.dojo.jpa.UserAccount;

@Stateless
public class Editor{
	
	public boolean editUser(UserAccount user) {	
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("dojo-jpa");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		UserAccount u = entityManager.find(UserAccount.class, user.getId());
		u.setEmailAddress(user.getEmailAddress());
		u.setFirstname(user.getFirstname());
		u.setLastname(user.getLastname());
		entityManager.getTransaction().commit();
		entityManager.close();
		return true;		
	}
}
