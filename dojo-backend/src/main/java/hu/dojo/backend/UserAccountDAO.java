package hu.dojo.backend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.dojo.jpa.UserAccount;

@Stateless
public class UserAccountDAO implements IEntityDAO<UserAccount> {

	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "dojo-jpa")
	private EntityManager entityManager;

	@Override
	public List<UserAccount> fetchMultiple(Map<String, Object> filterData) {
		String sql = "SELECT ua FROM UserAccount ua ";
		if (filterData.size() > 0) {
			sql += " WHERE ";
			Iterator it = filterData.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Object> entry = (Entry<String, Object>) it.next();
				String key = entry.getKey();
				sql += "ua. " + key + " LIKE :" + key + " ";
				if (it.hasNext()) {
					sql += " AND ";
				}
			}
		}
		TypedQuery<UserAccount> query = entityManager.createQuery(sql, UserAccount.class);
		if (filterData.size() > 0) {
			sql += " WHERE ";
			Iterator it = filterData.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Object> entry = (Entry<String, Object>) it.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				query.setParameter(key, "%" + value + "%");
			}
		}

		List<UserAccount> resultList = query.getResultList();
		if (resultList == null || resultList.size() < 1) {
			return new ArrayList<UserAccount>();
		}
		return resultList;
	}

	@Override
	public UserAccount fetch(Long id) {
		return null;
	}

	@Override
	public void persist(UserAccount entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {

	}

}