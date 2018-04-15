package hu.dojo.backend;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import hu.dojo.jpa.AbstractEntity;
import hu.dojo.jpa.Train;
import hu.dojo.jpa.UserAccount;

@Local
public interface IEntityDAO<T extends AbstractEntity> extends Serializable{

	
	public List<T> fetchMultiple(Map<String, Object> filterData);

	public T fetch(Long id);

	public void persist(T entity);

	public void delete(Long id);
}
