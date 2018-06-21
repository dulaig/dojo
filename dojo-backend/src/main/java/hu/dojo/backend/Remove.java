package hu.dojo.backend;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.dojo.jpa.UserAccount;

@Stateless
public class Remove{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "dojo-jpa")
	private EntityManager entityManager;

	public Remove() {
	}

	public boolean remove(List<UserAccount> users) {
		if (users != null && users.size() > 0)
			for (int i = 0; i < users.size(); i++) {
				updateTable(users.get(i).getId());
			}			
		else
			return false;
		return true;
	}
	private void updateTable(Long id) {
		
	}

	
}
