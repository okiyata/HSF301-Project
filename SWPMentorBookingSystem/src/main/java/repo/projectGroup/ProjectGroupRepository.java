package repo.projectGroup;

import java.util.List;

import pojo.ProjectGroup;
import pojo.Student;

public interface ProjectGroupRepository {
	ProjectGroup save(ProjectGroup projectGroup);

	ProjectGroup update(ProjectGroup projectGroup);

	void delete(ProjectGroup projectGroup);

	void deleteById(Integer projectGroupId);

	ProjectGroup findById(Integer projectGroupId);

	List<ProjectGroup> findAll();

	List<ProjectGroup> findByName(String name);

	ProjectGroup findGroupByStudentId(int studentID);

	boolean addMemberToGroup(int groupID, int studentID);
	
	int getMemberCount(int groupID);
	
	List<Student> getMembers(int groupID);
}
