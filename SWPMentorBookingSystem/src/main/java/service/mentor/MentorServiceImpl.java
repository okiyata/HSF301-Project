package service.mentor;

import java.util.List;

import pojo.Mentor;
import repo.mentor.MentorRepository;
import repo.mentor.MentorRepositoryImpl;

public class MentorServiceImpl implements MentorService{
	
	private MentorRepository repo;
	
	public MentorServiceImpl () {
		repo = new MentorRepositoryImpl();
	}
	
	@Override
	public Mentor save(Mentor mentor) {
		return repo.save(mentor);
	}

	@Override
	public Mentor update(Mentor mentor) {
		return repo.update(mentor);
	}

	@Override
	public void delete(Mentor mentor) {
		repo.delete(mentor);
	}
	
	@Override
	public void deleteById (Integer mentorId) {
		repo.deleteById(mentorId);
	}

	@Override
	public Mentor findById(Integer mentorId) {
		return repo.findById(mentorId);
	}

	@Override
	public List<Mentor> findAll() {
		return repo.findAll();
	}

}
