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

import hu.dojo.jpa.Train;

@Stateless
public class TrainDAO implements IEntityDAO<Train>{

	@PersistenceContext(unitName="dojo-jpa")
	private EntityManager entityManager;
	
	@Override
	public List<Train> fetchMultiple(Map<String, Object> filterData){
		String sql ="SELECT t FROM Train t ";		
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
		TypedQuery<Train> query = entityManager.createQuery(sql, Train.class);
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
		
		List<Train> resultList = query.getResultList();
		if(resultList == null || resultList.size() < 1) {
			return new ArrayList<Train>();
		}
		return resultList;
		
	}	

	@Override
	public Train fetch(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void persist(Train entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
}
