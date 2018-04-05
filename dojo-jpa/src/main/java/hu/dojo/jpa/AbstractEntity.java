package hu.dojo.jpa;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) /*AUTO-INCREMENT SQL-BEN*/
	private Long id;
	@Column(name="CREATION_DATE")
	private LocalDateTime creationDate;
	@Column(name="MODIFICATION_DATE")
	private LocalDateTime modificationDate;
	/*@Column
	private Long version;
	@Column
	private boolean deleted;*/
	
	@PrePersist /*Minden insert-nél lefut*/
	private void prePersist() {
		creationDate = LocalDateTime.now();
	}
	@PreUpdate /*Minden update-nél lefut*/
	private void preUpdate() {
		modificationDate = LocalDateTime.now();
	}
	
	/*public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	public LocalDateTime getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(LocalDateTime modificationDate) {
		this.modificationDate = modificationDate;
	}
	/*public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}*/
}
