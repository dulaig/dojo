package hu.dojo.backend;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.dojo.jpa.UserAccount;

@Stateless
public class UserAccountDAO implements IEntityDAO<UserAccount> {

	@PersistenceContext(unitName = "dojo-jpa")
	private EntityManager entityManger;

	@Override
	public List<UserAccount> fetchMultiple() {
		TypedQuery<UserAccount> query = entityManger.createQuery("SELECT ua FROM UserAccount ua", UserAccount.class);
		List<UserAccount> resultList = query.getResultList();
		if(resultList == null || resultList.size() < 1) {
			return new ArrayList<UserAccount>();
		}
		return resultList;
	}

	@Override
	public UserAccount fetch(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void persist(UserAccount entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

}
