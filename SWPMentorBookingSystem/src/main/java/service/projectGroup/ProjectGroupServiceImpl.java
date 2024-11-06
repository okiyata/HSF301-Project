package service.projectGroup;

import java.util.List;

import pojo.ProjectGroup;
import pojo.Student;
import repo.projectGroup.ProjectGroupRepository;
import repo.projectGroup.ProjectGroupRepositoryImpl;
import repo.student.StudentRepository;
import repo.student.StudentRepositoryImpl;

public class ProjectGroupServiceImpl implements ProjectGroupService {

	private ProjectGroupRepository projectGroupRepository;
	private StudentRepository studentRepository;

	public ProjectGroupServiceImpl() {
		projectGroupRepository = new ProjectGroupRepositoryImpl();
		studentRepository = new StudentRepositoryImpl();
	}

	@Override
	public ProjectGroup save(ProjectGroup projectGroup) {
		return projectGroupRepository.save(projectGroup);
	}

	@Override
	public ProjectGroup update(ProjectGroup projectGroup) {
		return projectGroupRepository.update(projectGroup);
	}

	@Override
	public void delete(ProjectGroup projectGroup) {
		projectGroupRepository.delete(projectGroup);
	}

	@Override
	public void deleteById(Integer projectGroupId) {
		projectGroupRepository.deleteById(projectGroupId);
	}

	@Override
	public ProjectGroup findById(Integer projectGroupId) {
		return projectGroupRepository.findById(projectGroupId);
	}

	@Override
	public List<ProjectGroup> findAll() {
		return projectGroupRepository.findAll();
	}

	@Override
	public List<ProjectGroup> findByName(String name) {
		return projectGroupRepository.findByName(name);
	}

	@Override
	public ProjectGroup createGroup(String topic, String groupName, int studentID) {
		Student student = studentRepository.findById(studentID);
		if (student == null || student.getProjectGroup() != null) {
			throw new IllegalArgumentException("Student không hợp lệ hoặc đã có nhóm.");
		}

		ProjectGroup newGroup = new ProjectGroup(topic, groupName, "Not Started", 10);
		projectGroupRepository.save(newGroup);

		student.setProjectGroup(newGroup);
		studentRepository.update(student);

		return newGroup;
	}

	@Override
	public ProjectGroup findGroupByStudentId(int studentID) {
		return projectGroupRepository.findGroupByStudentId(studentID);
	}

	@Override
	public boolean addMemberToGroup(int groupID, int studentID) {
		return projectGroupRepository.addMemberToGroup(groupID, studentID);
	}

	@Override
	public int getMemberCount(int groupID) {
		return projectGroupRepository.getMemberCount(groupID);
	}

	@Override
	public List<Student> getMembers(int groupID) {
		return projectGroupRepository.getMembers(groupID);
	}

}
