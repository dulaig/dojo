package hu.dojo.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ACCOUNT")
public class UserAccount  extends AbstractEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8910291877329629546L;

	@Column(nullable = false, unique = true, name = "EMAIL_ADDRESS")
	private String emailAddress;

	@Column(nullable = false)
	private String password;

	@Column
	private String firstname;

	@Column
	private String lastname;	

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}