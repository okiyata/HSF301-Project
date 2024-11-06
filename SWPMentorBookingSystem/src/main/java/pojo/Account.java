package pojo;

import javax.persistence.*;

@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountID;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String role;

	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Student student;

	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Mentor mentor;

	public Account() {
		super();
	}

	public Account(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public Account(String username, String password, String role, Student student, Mentor mentor) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.student = student;
		this.mentor = mentor;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Mentor getMentor() {
		return mentor;
	}

	public void setMentor(Mentor mentor) {
		this.mentor = mentor;
	}

	@Override
	public String toString() {
		return "Account [accountID=" + accountID + ", username=" + username + ", password=" + password + ", role="
				+ role + "]";
	}

}
