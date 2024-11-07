package pojo;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int appointmentID;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "projectGroupID", nullable = false)
	private ProjectGroup projectGroup;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mentorID", nullable = false)
	private Mentor mentor;

	@Column(nullable = false)
	private LocalDateTime dateTime;

	@Column(nullable = false)
	private String skillRequested;

	@Column(nullable = false)
	private int fee;

	@Column(nullable = false)
	private String status; // AWAIT_APPROVAL, APPROVE, DENIED, FINISHED

	public Appointment() {
		super();
	}

	public Appointment(ProjectGroup projectGroup, Mentor mentor, LocalDateTime dateTime, String skillRequested, int fee,
			String status) {
		this.projectGroup = projectGroup;
		this.mentor = mentor;
		this.dateTime = dateTime;
		this.skillRequested = skillRequested;
		this.fee = fee;
		this.status = status;
	}

	public int getAppointmentID() {
		return appointmentID;
	}

	public void setAppointmentID(int appointmentID) {
		this.appointmentID = appointmentID;
	}

	public ProjectGroup getProjectGroup() {
		return projectGroup;
	}

	public void setProjectGroup(ProjectGroup projectGroup) {
		this.projectGroup = projectGroup;
	}

	public Mentor getMentor() {
		return mentor;
	}

	public void setMentor(Mentor mentor) {
		this.mentor = mentor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getSkillRequested() {
		return skillRequested;
	}

	public void setSkillRequested(String skillRequested) {
		this.skillRequested = skillRequested;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	@Override
	public String toString() {
		return "Appointment [appointmentID=" + appointmentID + ", dateTime=" + dateTime + ", skillRequested="
				+ skillRequested + ", fee=" + fee + ", status=" + status + "]";
	}

}
