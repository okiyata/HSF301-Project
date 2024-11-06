package pojo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class ProjectGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int groupID;

	@Column(nullable = false)
	private String groupName;
	
	@Column(nullable = false)
	private String topic;

	@OneToMany(mappedBy = "projectGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Student> members = new ArrayList<>();

	@Column(nullable = false)
	private String progress;

	@Column(nullable = false)
	private int walletPoints;

	@OneToMany(mappedBy = "projectGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Appointment> appointments = new ArrayList<>();

	public ProjectGroup() {
		super();
	}

	public ProjectGroup(String topic, String groupName, List<Student> members, String progress, int walletPoints, List<Appointment> appointments) {
		this.topic = topic;
		this.groupName = groupName;
		this.members = members;
		this.progress = progress;
		this.groupName = groupName;
		this.appointments = appointments;
	}

	public ProjectGroup(String topic, String groupName, String progress, int walletPoints) {
		this.topic = topic;
		this.groupName = groupName;
		this.progress = progress;
		this.walletPoints = walletPoints;
	}

	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public List<Student> getMembers() {
		return members;
	}

	public void setMembers(List<Student> members) {
		this.members = members;
	}

	public void addMember(Student student) {
		student.setProjectGroup(this);
		this.members.add(student);
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public int getWalletPoints() {
		return walletPoints;
	}

	public void setWalletPoints(int walletPoints) {
		this.walletPoints = walletPoints;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public void addAppointment(Appointment appointment) {
		appointment.setProjectGroup(this);
		this.appointments.add(appointment);
	}

	@Override
	public String toString() {
		return "ProjectGroup [groupID=" + groupID + ", topic=" + topic + ", progress=" + progress + ", walletPoints="
				+ walletPoints + "]";
	}

}
