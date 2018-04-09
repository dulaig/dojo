package hu.dojo.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.dojo.jpa.Train;

public class TrainDAO implements IEntityDAO<Train>{
	
	@PersistenceContext(unitName="dojo-jpa")
	private EntityManager entityManager;

	@Override
	public List<Train> fetchMultiple(Map<String, Object> filterData) {
		String sql = "SELECT t FROM Train t";
		TypedQuery<Train> query = entityManager.createQuery(sql,Train.class);
		List<Train> trainList = query.getResultList();
		if(trainList != null && trainList.size() > 0)
			return trainList;
		return new ArrayList<Train>();
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
