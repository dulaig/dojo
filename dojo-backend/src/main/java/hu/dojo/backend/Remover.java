package hu.dojo.backend;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import hu.dojo.jpa.AbstractEntity;
import hu.dojo.jpa.Train;
import hu.dojo.jpa.Trip;
import hu.dojo.jpa.UserAccount;

@Stateless
public class Remover{

	public boolean entityRemove(List<AbstractEntity> entities, String entityType) {
		if (entities != null && entities.size() > 0)
			for (int i = 0; i < entities.size(); i++) {				
				updateTable(entities.get(i).getId(), entityType);
			}			
		else
			return false;
		return true;
	}
	private void updateTable(Long id, String entityType) {		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("dojo-jpa");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		if(entityType == "user") {
			UserAccount u = entityManager.find(UserAccount.class, id);			
			u.setDeleted(true);
		}else if(entityType == "train") {
			Train tn = entityManager.find(Train.class, id);
			tn.setDeleted(true);
		}else if(entityType == "trip") {
			Trip tp = entityManager.find(Trip.class, id);
			tp.setDeleted(true);
		}
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
}
