package hu.dojo.backend;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import hu.dojo.jpa.UserAccount;

@Local
public interface IEntityDAO<T> extends Serializable{

	List<UserAccount> Label = null;

	public List<T> fetchMultiple();

	public T fetch(Long id);

	public void persist(T entity);

	public void delete(Long id);
}
