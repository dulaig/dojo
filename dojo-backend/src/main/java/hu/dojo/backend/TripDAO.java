package hu.dojo.backend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.dojo.jpa.Trip;

public class TripDAO implements IEntityDAO<Trip>{

	@PersistenceContext(unitName="dojo-jpa")
	private EntityManager entityManager;
	
	@Override
	public List<Trip> fetchMultiple(Map<String, Object> filterData) {
		String sql ="SELECT t FROM Trip t ";		
		if(filterData.size()>0) {
			sql += " WHERE ";
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
