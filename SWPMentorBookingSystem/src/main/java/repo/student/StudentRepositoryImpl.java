package repo.student;

import java.util.List;

import dao.StudentDAO;
import pojo.Student;

public class StudentRepositoryImpl implements StudentRepository {

	private StudentDAO dao;

	public StudentRepositoryImpl() {
		dao = new StudentDAO();
	}

	@Override
	public Student save(Student student) {
		dao.save(student);
		return dao.findById(student.getStudentID());
	}

	@Override
	public Student update(Student student) {
		dao.update(student);
		return dao.findById(student.getStudentID());
	}

	@Override
	public void delete(Student student) {
		dao.delete(student);
	}

	@Override
	public void deleteById(Integer studentId) {
		delete(dao.findById(studentId));
	}

	@Override
	public Student findById(Integer studentId) {
		return dao.findById(studentId);
	}

	@Override
	public List<Student> findAll() {
		return dao.findAll();
	}

	@Override
	public Student findByName(String studentName) {
		return dao.findByName(studentName);
	}

}
