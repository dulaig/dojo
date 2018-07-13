package hu.dojo.backend;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import hu.dojo.jpa.Train;
import hu.dojo.jpa.UserAccount;

@Stateless
public class Editor{
	
	EntityManagerFactory entityManagerFactory;
	EntityManager entityManager;
	
	private void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory("dojo-jpa");
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
	}
	
	public void editUser(UserAccount user) {	
		init();		
		UserAccount u = entityManager.find(UserAccount.class, user.getId());
		u.setEmailAddress(user.getEmailAddress());
		u.setFirstname(user.getFirstname());
		u.setLastname(user.getLastname());
		entityManager.getTransaction().commit();
		entityManager.close();	
	}
	public void editTrain(Train train) {
		init();
		Train t = entityManager.find(Train.class,train.getId());
		t.setSerialCode(train.getSerialCode());
		t.setType(train.getType());
		t.setColour(train.getColour());
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
