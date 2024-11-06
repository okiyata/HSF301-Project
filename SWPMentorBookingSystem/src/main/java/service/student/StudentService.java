package service.student;

import java.util.List;

import pojo.Student;

public interface StudentService {
	Student save(Student student);

	Student update(Student student);

	void delete(Student student);

	void deleteById(Integer studentId);

	Student findById(Integer studentId);
	
	Student findByName(String studentName);

	List<Student> findAll();
	
	public boolean hasGroup(int studentID);
	
	boolean joinGroup(int groupID, int studentID);
}
