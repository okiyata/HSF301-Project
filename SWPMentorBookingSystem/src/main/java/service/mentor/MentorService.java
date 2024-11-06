package service.mentor;

import java.util.List;

import pojo.Mentor;

public interface MentorService {
	Mentor save(Mentor mentor);

	Mentor update(Mentor mentor);

	void delete(Mentor mentor);

	void deleteById(Integer mentorId);

	Mentor findById(Integer mentorId);

	List<Mentor> findAll();
}
