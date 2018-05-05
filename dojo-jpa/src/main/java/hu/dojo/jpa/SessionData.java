package hu.dojo.jpa;

public class SessionData {

	private static SessionData instance;
	private String firstName;
	private String lastName;
	private String email;
	
	public static SessionData getInstance(){
		if(instance == null) {
			instance = new SessionData();
		}
		return instance;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
