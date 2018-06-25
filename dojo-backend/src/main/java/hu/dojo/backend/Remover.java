package hu.dojo.backend;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import hu.dojo.jpa.UserAccount;

@Stateless
public class Remover{

	public Remover() {		
	}

	public boolean userRemove(List<UserAccount> users) {
		if (users != null && users.size() > 0)
			for (int i = 0; i < users.size(); i++) {
				updateTable(users.get(i).getId());
			}			
		else
			return false;
		return true;
	}
	private void updateTable(Long id) {		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("dojo-jpa");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		UserAccount u = entityManager.find(UserAccount.class, id);
		u.setDeleted(true);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
}
