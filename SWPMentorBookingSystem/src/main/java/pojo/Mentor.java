package pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Mentor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int mentorID;

	@Column(nullable = false)
	private String mentorName;

	@Column(nullable = false)
	private String skills;

	@Column(nullable = false)
	private String availability;

	@Column(nullable = false)
	private int bookingFee;

	@Column(nullable = false)
	private String status; // AVAILABLE | UNAVAILABLE

	@OneToOne
	@JoinColumn(name = "accountID", nullable = false)
	private Account account;

	@OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Appointment> appointments = new ArrayList<>();

	public Mentor() {
	}

	public Mentor(String mentorName, String skills, String availability, int bookingFee, String status,
			Account account) {
		super();
		this.mentorName = mentorName;
		this.skills = skills;
		this.availability = availability;
		this.bookingFee = bookingFee;
		this.status = status;
		this.account = account;
	}

	public int getMentorID() {
		return mentorID;
	}

	public void setMentorID(int mentorID) {
		this.mentorID = mentorID;
	}

	public String getMentorName() {
		return mentorName;
	}

	public void setMentorName(String mentorName) {
		this.mentorName = mentorName;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public int getBookingFee() {
		return bookingFee;
	}

	public void setBookingFee(int bookingFee) {
		this.bookingFee = bookingFee;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public void addAppointment(Appointment appointment) {
		appointment.setMentor(this);
		this.appointments.add(appointment);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Mentor [mentorID=" + mentorID + ", mentorName=" + mentorName + ", skills=" + skills + ", availability="
				+ availability + ", bookingFee=" + bookingFee + ", status=" + status + "]";
	}

}
