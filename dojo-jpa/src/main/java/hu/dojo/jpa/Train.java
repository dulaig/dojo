<<<<<<< HEAD
package hu.dojo.jpa;

import javax.persistence.Column;

public class Train extends AbstractEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name= "SERIAL_CODE")
	private String serialCode;
	
	@Column
	private TrainType type;
	
	@Column
	private Colour colour;
	
	public String getSerialCode() {
		return serialCode;
	}
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}
	public TrainType getType() {
		return type;
	}
	public void setType(TrainType type) {
		this.type = type;
	}
	public Colour getColour() {
		return colour;
	}
	public void setColour(Colour colour) {
		this.colour = colour;
	}
	
	
}
=======
package hu.dojo.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Train extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2179285967804246815L;

	@Column(name = "SERIAL_CODE")
	private String serialCode;

	@Column
	private TrainType type;

	@Column
	private Colour colour;

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public TrainType getType() {
		return type;
	}

	public void setType(TrainType type) {
		this.type = type;
	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}
}
>>>>>>> dulaig
