package repo.mentor;

import java.util.List;

import dao.MentorDAO;
import pojo.Appointment;
import pojo.Mentor;

public class MentorRepositoryImpl implements MentorRepository {

	private MentorDAO dao;

	public MentorRepositoryImpl() {
		dao = new MentorDAO();
	}

	@Override
	public Mentor save(Mentor mentor) {
		dao.save(mentor);
		return dao.findById(mentor.getMentorID());
	}

	@Override
	public Mentor update(Mentor mentor) {
		dao.update(mentor);
		return dao.findById(mentor.getMentorID());
	}

	@Override
	public void delete(Mentor mentor) {
		dao.delete(mentor);
	}

	@Override
	public void deleteById(Integer mentorId) {
		delete(dao.findById(mentorId));
	}

	@Override
	public Mentor findById(Integer mentorId) {
		return dao.findById(mentorId);
	}

	@Override
	public List<Mentor> findAll() {
		return dao.findAll();
	}

	@Override
	public List<Mentor> getAvailableMentors() {
		return dao.getAvailableMentors();
	}
  
  @Override
	public List<Appointment> findAppointmentsByMentorId(int mentorID) {
		return dao.findAppointmentsByMentorId(mentorID);
	}

	@Override
	public List<Appointment> findHistoryByMentorId(int mentorID) {
		return dao.findHistoryByMentorId(mentorID);
	}

}
