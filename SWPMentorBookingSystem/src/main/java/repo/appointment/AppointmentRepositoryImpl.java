package repo.appointment;

import java.util.List;

import dao.AppointmentDAO;
import pojo.Appointment;

public class AppointmentRepositoryImpl implements AppointmentRepository {

	private AppointmentDAO dao;

	public AppointmentRepositoryImpl() {
		dao = new AppointmentDAO();
	}

	@Override
	public Appointment save(Appointment appointment) {
		dao.save(appointment);
		return dao.findById(appointment.getAppointmentID());
	}

	@Override
	public Appointment update(Appointment appointment) {
		dao.update(appointment);
		return dao.findById(appointment.getAppointmentID());
	}

	@Override
	public void delete(Appointment appointment) {
		dao.delete(appointment);
	}

	@Override
	public void deleteById(Integer appointmentId) {
		delete(dao.findById(appointmentId));
	}

	@Override
	public Appointment findById(Integer appointmentId) {
		return dao.findById(appointmentId);
	}

	@Override
	public List<Appointment> findAll() {
		return dao.findAll();
	}

	@Override
	public List<Appointment> getAppointmentsByGroupId(int groupId) {
		return dao.getAppointmentsByGroupId(groupId);
	}

	@Override
	public List<Appointment> findFinishedAppointmentsWithoutGroupRating(int groupId) {
		return dao.findFinishedAppointmentsWithoutGroupRating(groupId);
	}

	@Override
	public List<Appointment> findFinishedAppointmentsWithoutMentorRating(int mentorId) {
		return dao.findFinishedAppointmentsWithoutMentorRating(mentorId);
	}

}
