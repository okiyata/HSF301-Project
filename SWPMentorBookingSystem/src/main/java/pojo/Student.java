package pojo;

import javax.persistence.*;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int studentID;

	@Column(nullable = false)
	private String studentName;

	@OneToOne
	@JoinColumn(name = "accountID", nullable = false)
	private Account account;

	@ManyToOne
	@JoinColumn(name = "groupID")
	private ProjectGroup projectGroup;

	public Student() {
	}

	public Student(String studentName, Account account, ProjectGroup projectGroup) {
		this.studentName = studentName;
		this.account = account;
		this.projectGroup = projectGroup;
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public ProjectGroup getProjectGroup() {
		return projectGroup;
	}

	public void setProjectGroup(ProjectGroup projectGroup) {
		this.projectGroup = projectGroup;
	}

	@Override
	public String toString() {
		return "Student [studentID=" + studentID + ", studentName=" + studentName + "]";
	}

}
