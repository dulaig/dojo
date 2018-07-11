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

import hu.dojo.jpa.Trip;

@Stateless
public class TripDAO implements IEntityDAO<Trip>{

	@PersistenceContext(unitName="dojo-jpa")
	private EntityManager entityManager;
	
	@Override
	public List<Trip> fetchMultiple(Map<String, Object> filterData) {
		String sql ="SELECT t FROM Trip t WHERE t.deleted = 0";		
		if(filterData.size()>0) {
			sql += " AND ";
			Iterator it = filterData.entrySet().iterator();
			while(it.hasNext()) {
				Entry<String, Object> entry = (Entry<String, Object>) it.next();
				String key = entry.getKey();
				sql += "t. " + key + " LIKE :" + key + " ";
				if(it.hasNext()) {
					sql += " AND ";
				}
			}
		}		
		TypedQuery<Trip> query = entityManager.createQuery(sql, Trip.class);
		if(filterData.size()>0) {
			sql += " WHERE ";
			Iterator it = filterData.entrySet().iterator();
			while(it.hasNext()) {
				Entry<String, Object> entry = (Entry<String, Object>) it.next();
				String key = entry.getKey();
				Object value  = entry.getValue();
				query.setParameter(key,"%" + value + "%");
			}
		}	
		
		List<Trip> resultList = query.getResultList();
		if(resultList == null || resultList.size() < 1) {
			return new ArrayList<Trip>();
		}
		return resultList;
	}
	
	/*
	@Override
	public List<Trip> fetchMultiple(Map<String, Object> filterData) {
		String sql = "SELECT t FROM Trip t";
		TypedQuery<Trip> query = entityManager.createQuery(sql, Trip.class);
		List<Trip> tripList = query.getResultList();
		if (tripList != null && tripList.size() > 0)
			return tripList;
		return new ArrayList<Trip>();
	}*/

	@Override
	public Trip fetch(Long id) {
		Trip trip = entityManager.find(Trip.class, id);
		return trip;
	}

	@Override
	public void persist(Trip entity) {
		entityManager.persist(entity);
		entityManager.flush();
		
	}

	@Override
	public void delete(Long id) {
		entityManager.remove(fetch(id));
		entityManager.flush();
		
	}

}
