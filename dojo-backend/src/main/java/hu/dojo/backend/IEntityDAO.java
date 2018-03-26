package hu.dojo.backend;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

@Local
public interface IEntityDAO<T> extends Serializable {

	public List<T> fetchMultiple(Map<String, Object> filterData);

	public T fetch(Long id);

	public void persist(T entity);

	public void delete(Long id);
}
