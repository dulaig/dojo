package hu.dojo.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.dojo.jpa.Train;
import hu.dojo.jpa.Trip;

public class TripDAO implements IEntityDAO<Trip> {

	@PersistenceContext(unitName = "dojo-jpa")
	private EntityManager entityManager;

	@Override
	public List<Trip> fetchMultiple(Map<String, Object> filterData) {
		String sql = "SELECT t FROM Trip t";
		TypedQuery<Trip> query = entityManager.createQuery(sql, Trip.class);
		List<Trip> trainList = query.getResultList();
		if (trainList != null && trainList.size() > 0)
			return trainList;
		return new ArrayList<Trip>();
	}

	@Override
	public Trip fetch(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void persist(Trip entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

}
