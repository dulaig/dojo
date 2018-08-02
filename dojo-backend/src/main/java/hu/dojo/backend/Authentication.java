package hu.dojo.backend;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import hu.dojo.jpa.AbstractEntity;
import hu.dojo.jpa.UserAccount;

@Stateless
public class Authentication extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "dojo-jpa")
	private EntityManager entityManager;

	public void registration(UserAccount entity) {
		entityManager.persist(entity);
		entityManager.flush();
	}

	public UserAccount findUser(String email, String password) {
		String sql = "SELECT ua FROM UserAccount ua WHERE ua.emailAddress = '" + email + "'";
		if (password != null && !"".equals(password))
			sql += " AND ua.password = '" + password + "' ";
		TypedQuery<UserAccount> query = entityManager.createQuery(sql, UserAccount.class);
		if (query.getResultList().size() > 0)
			return query.getSingleResult();
		return null;
	}

	public UserAccount fetch(Long id) {
		UserAccount user = entityManager.find(UserAccount.class, id);
		return user;
	}

	public boolean validateEmail(UserAccount entity) {
		if (findUser(entity.getEmailAddress(), "") == null)
			return true;
		else
			return false;
	}
}
