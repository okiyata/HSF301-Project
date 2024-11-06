package pojo;

import javax.persistence.*;

@Entity
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int notificationID;

	@Column(nullable = false)
	private String message;

	@Column(nullable = false)
	private boolean isRead;

	@Column(nullable = false)
	private String notificationType; // "MENTOR" hoặc "GROUP"

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mentorID")
	private Mentor mentor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "groupID")
	private ProjectGroup projectGroup;

	public Notification() {
	}

	public Notification(String message, boolean isRead, String notificationType, Mentor mentor,
			ProjectGroup projectGroup) {
		this.message = message;
		this.isRead = isRead;
		this.notificationType = notificationType;
		this.mentor = mentor;
		this.projectGroup = projectGroup;
	}

	public int getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(int notificationID) {
		this.notificationID = notificationID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public Mentor getMentor() {
		return mentor;
	}

	public void setMentor(Mentor mentor) {
		this.mentor = mentor;
	}

	public ProjectGroup getProjectGroup() {
		return projectGroup;
	}

	public void setProjectGroup(ProjectGroup projectGroup) {
		this.projectGroup = projectGroup;
	}

	@Override
	public String toString() {
		return "Notification [notificationID=" + notificationID + ", message=" + message + ", isRead=" + isRead
				+ ", notificationType=" + notificationType + "]";
	}

}
