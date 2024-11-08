package pojo;

import javax.persistence.*;

@Entity
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ratingID;

	@Column(nullable = false)
	private int rating;

	@Column(nullable = true)
	private String feedback;

	@Column(nullable = false)
	private String ratingType; // "MENTOR" hoặc "GROUP"

	@ManyToOne
	@JoinColumn(name = "appointment_id", nullable = false)
	private Appointment appointment;

	public Rating() {
	}

	public Rating(int rating, String feedback, String ratingType, Appointment appointment) {
		super();
		this.rating = rating;
		this.feedback = feedback;
		this.ratingType = ratingType;
		this.appointment = appointment;
	}

	public int getRatingID() {
		return ratingID;
	}

	public void setRatingID(int ratingID) {
		this.ratingID = ratingID;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getRatingType() {
		return ratingType;
	}

	public void setRatingType(String ratingType) {
		this.ratingType = ratingType;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public int getAppointmentID() {
		return appointment != null ? appointment.getAppointmentID() : null;
	}

	@Override
	public String toString() {
		return "Rating [ratingID=" + ratingID + ", rating=" + rating + ", feedback=" + feedback + ", ratingType="
				+ ratingType + "]";
	}

}