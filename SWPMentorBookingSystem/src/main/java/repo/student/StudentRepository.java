package repo.student;

import java.util.List;

import pojo.Student;

public interface StudentRepository {
	Student save(Student student);

	Student update(Student student);

	void delete(Student student);

	void deleteById(Integer studentId);

	Student findById(Integer studentId);

	List<Student> findAll();
}
