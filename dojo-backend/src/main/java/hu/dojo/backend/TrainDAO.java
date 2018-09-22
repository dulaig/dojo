package hu.dojo.backend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.dojo.jpa.Colour;
import hu.dojo.jpa.Train;
import hu.dojo.jpa.TrainType;

@Stateless
public class TrainDAO implements IEntityDAO<Train> {

	@PersistenceContext(unitName = "dojo-jpa")
	private EntityManager entityManager;

	@Override
	public List<Train> fetchMultiple(Map<String, Object> filterData) {
		String sql = "SELECT t FROM Train t WHERE t.deleted = 0 ";

		if (filterData.size() > 0) {
			int id = (int) (filterData.get("type") == null ? filterData.get("colour") : filterData.get("type"));
			if (id != -1) {
				sql += " AND ";
				Iterator it = filterData.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, Object> entry = (Entry<String, Object>) it.next();
					String key = entry.getKey();
					sql += "t. " + key + "= :" + key;					
					if (it.hasNext()) {
						sql += " AND ";
					}
				}
			}
		}
		TypedQuery<Train> query = entityManager.createQuery(sql, Train.class);
		if (filterData.size() > 0) {
			sql += " WHERE ";
			Iterator it = filterData.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Object> entry = (Entry<String, Object>) it.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				if ((int) value != -1) {
					int index = (int) value;
					Object[] array;
					if ("type".equals(key))
						array = TrainType.values();
					else
						array = Colour.values();
					System.out.println("KEY: " + key + " VALUE: " + array[index]);
					query.setParameter(key, array[index]);
				}
			}
		}
		List<Train> resultList = query.getResultList();
		if (resultList == null || resultList.size() < 1) {
			return new ArrayList<Train>();
		}
		return resultList;

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
