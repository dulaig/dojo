package hu.dojo.backend;

import java.util.List;

import javax.ejb.Local;

@Local
public interface IEntityDAO<T> {

	public List<T> fetchMultiple();

	public T fetch(Long id);

	public void persist(T entity);

	public void delete(Long id);
}
