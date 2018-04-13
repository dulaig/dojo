package hu.dojo.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.dojo.jpa.Train;

@Stateless
@Remote(Train.class)
public class TrainDAO implements IEntityDAO<Train> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "dojo-jpa")
	private EntityManager entityManager;

	@Override
	public List<Train> fetchMultiple(Map<String, Object> filterData) {
		String sql = "SELECT t FROM Train t";
		TypedQuery<Train> query = entityManager.createQuery(sql, Train.class);
		List<Train> trainList = query.getResultList();
		if (trainList != null && trainList.size() > 0)
			return trainList;
		return new ArrayList<Train>();
	}

	@Override
	public Train fetch(Long id) {
		Train train = entityManager.find(Train.class, id);
		return train;
	}

	@Override
	public void persist(Train entity) {
		entityManager.persist(entity);
		entityManager.flush();
	}

	@Override
	public void delete(Long id) {
		entityManager.remove(fetch(id));
		entityManager.flush();
	}

}
