package hu.dojo.backend;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.xml.registry.infomodel.EmailAddress;

import hu.dojo.jpa.AbstractEntity;
import hu.dojo.jpa.UserAccount;

@Stateless
public class Authentication extends AbstractEntity{

	@PersistenceContext(unitName="dojo-jpa")
	private EntityManager entityManager;
	
	public void registration(UserAccount entity) {		
		entityManager.persist(entity);
		entityManager.flush();				
	}
	
	public void login() {
		
	}
	
	public UserAccount findUser(String email, String password) {		
		String sql = "SELECT ua FROM UserAccount ua WHERE ua.emailAddress = '" + email + "' AND ua.password = '" + password + "' ";
		TypedQuery<UserAccount> query = entityManager.createQuery(sql, UserAccount.class);
		UserAccount user = query.getSingleResult();
		return user;
	}
	
	public UserAccount fetch(Long id) {		
		UserAccount user = entityManager.find(UserAccount.class, id);		
		return user;
	}
}
