package repo.projectGroup;

import java.util.List;

import pojo.ProjectGroup;

public interface ProjectGroupRepository {
	ProjectGroup save(ProjectGroup projectGroup);

	ProjectGroup update(ProjectGroup projectGroup);

	void delete(ProjectGroup projectGroup);

	void deleteById(Integer projectGroupId);

	ProjectGroup findById(Integer projectGroupId);

	List<ProjectGroup> findAll();
	
	List<ProjectGroup> findByName(String name);
	
	String getGroupNameById(int groupID);
}
