package hu.dojo.jpa;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TRIP")
public class Trip extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "TRAIN_ID")
	private int trainId;
	@Column(name = "CITY_FROM")
	private String from;
	@Column(name = "CITY_TO")
	private String to;
	@Column(name = "DEPARTURE")
	private LocalDateTime departure;
	@Column(name = "ARRIVAL")
	private LocalDateTime arrival;

	public int getTrainId() {
		return trainId;
	}

	public void setTrainId(int trainId) {
		this.trainId = trainId;
	}

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

	public String getDeparture() {
		return departure.toString().substring(11);
	}

	public void setDeparture(LocalDateTime departure) {
		this.departure = departure;
	}

	public String getArrival() {
		return arrival.toString().substring(11);
	}

	public void setArrival(LocalDateTime arrival) {
		this.arrival = arrival;
	}

}
