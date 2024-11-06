package repo.projectGroup;

import java.util.List;

import dao.ProjectGroupDAO;
import pojo.ProjectGroup;
import pojo.Student;

public class ProjectGroupRepositoryImpl implements ProjectGroupRepository {

	private ProjectGroupDAO dao;

	public ProjectGroupRepositoryImpl() {
		dao = new ProjectGroupDAO();
	}

	@Override
	public ProjectGroup save(ProjectGroup projectGroup) {
		dao.save(projectGroup);
		return dao.findById(projectGroup.getGroupID());
	}

	@Override
	public ProjectGroup update(ProjectGroup projectGroup) {
		dao.update(projectGroup);
		return dao.findById(projectGroup.getGroupID());
	}

	@Override
	public void delete(ProjectGroup projectGroup) {
		dao.delete(projectGroup);
	}

	@Override
	public void deleteById(Integer projectGroupId) {
		delete(dao.findById(projectGroupId));
	}

	@Override
	public ProjectGroup findById(Integer projectGroupId) {
		return dao.findById(projectGroupId);
	}

	@Override
	public List<ProjectGroup> findAll() {
		return dao.findAll();
	}

	@Override
	public List<ProjectGroup> findByName(String name) {
		return dao.findByName(name);
	}

	@Override
	public ProjectGroup findGroupByStudentId(int studentID) {
		return dao.findGroupByStudentId(studentID);
	}

	@Override
	public boolean addMemberToGroup(int groupID, int studentID) {
		return dao.addMemberToGroup(groupID, studentID);
	}

	@Override
	public int getMemberCount(int groupID) {
		return dao.getMemberCount(groupID);
	}

	@Override
	public List<Student> getMembers(int groupID) {
		return dao.getMembers(groupID);
	}

}
