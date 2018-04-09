package hu.dojo.jpa;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Trip extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private String from;
	@Column
	private String to;
	@Column
	private LocalDateTime departure;
	@Column
	private LocalDateTime arrival;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public LocalDateTime getDeparture() {
		return departure;
	}

	public void setDeparture(LocalDateTime departure) {
		this.departure = departure;
	}

	public LocalDateTime getArrival() {
		return arrival;
	}

	public void setArrival(LocalDateTime arrival) {
		this.arrival = arrival;
	}

}
