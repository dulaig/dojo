package hu.dojo.backend;

import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class UserAccountDAO implements IEntityDAO<UserAccountDAO> {

	@Override
	public List<UserAccountDAO> fetchMultiple() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserAccountDAO fetch(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void persist(UserAccountDAO entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

}
