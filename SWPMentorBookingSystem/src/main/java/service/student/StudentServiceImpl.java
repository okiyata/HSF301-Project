package service.student;

import java.util.List;

import pojo.ProjectGroup;
import pojo.Student;
import repo.projectGroup.ProjectGroupRepository;
import repo.projectGroup.ProjectGroupRepositoryImpl;
import repo.student.StudentRepository;
import repo.student.StudentRepositoryImpl;

public class StudentServiceImpl implements StudentService{
	
	private StudentRepository studentRepo;
	private ProjectGroupRepository proojectGroupRepo;
	
	public StudentServiceImpl () {
		studentRepo = new StudentRepositoryImpl();
		proojectGroupRepo = new ProjectGroupRepositoryImpl();
	}
	
	@Override
	public Student save(Student student) {
		return studentRepo.save(student);
	}

	@Override
	public Student update(Student student) {
		return studentRepo.update(student);
	}

	@Override
	public void delete(Student student) {
		studentRepo.delete(student);
	}
	
	@Override
	public void deleteById (Integer studentId) {
		studentRepo.deleteById(studentId);
	}

	@Override
	public Student findById(Integer studentId) {
		return studentRepo.findById(studentId);
	}

	@Override
	public List<Student> findAll() {
		return studentRepo.findAll();
	}

	@Override
	public boolean hasGroup(int studentID) {
        Student student = studentRepo.findById(studentID);
        return student != null && student.getProjectGroup() != null;
    }

	@Override
	public boolean joinGroup(int groupID, int studentID) {
        Student student = studentRepo.findById(studentID);
        ProjectGroup group = proojectGroupRepo.findById(groupID);

        if (student == null || group == null) {
            return false;
        }

        if (student.getProjectGroup() == null) {
            student.setProjectGroup(group);
            studentRepo.update(student);
            return true;
        } else {
            return false;
        }
    }

	@Override
	public Student findByName(String studentName) {
		return studentRepo.findByName(studentName);
	}

}
