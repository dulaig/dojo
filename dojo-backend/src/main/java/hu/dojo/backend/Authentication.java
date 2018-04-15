package hu.dojo.backend;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.dojo.jpa.UserAccount;

public class Authentication{

	@PersistenceContext(unitName = "dojo-jpa")
	private EntityManager entityManager;
	
	public void registration(UserAccount entity) {		
		entityManager.persist(entity);
		entityManager.flush();				
	}
	
	public void login() {
		
	}
	
	public UserAccount findUser(String email, String password) {
		String sql = "SELECT ua FROM UserAccount ua WHERE ua.EMAIL_ADDRESS = " + email + " AND ua.PASSWORD = " + password;
		TypedQuery<UserAccount> query = entityManager.createQuery(sql, UserAccount.class);
		UserAccount user = query.getSingleResult();
		return user;
	}
}
