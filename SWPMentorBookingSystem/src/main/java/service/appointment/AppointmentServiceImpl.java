package service.appointment;

import java.util.List;

import pojo.Appointment;
import repo.appointment.AppointmentRepository;
import repo.appointment.AppointmentRepositoryImpl;

public class AppointmentServiceImpl implements AppointmentService {

	private AppointmentRepository repo;

	public AppointmentServiceImpl() {
		repo = new AppointmentRepositoryImpl();
	}

	@Override
	public Appointment save(Appointment appointment) {
		return repo.save(appointment);
	}

	@Override
	public Appointment update(Appointment appointment) {
		return repo.update(appointment);
	}

	@Override
	public void delete(Appointment appointment) {
		repo.delete(appointment);
	}

	@Override
	public void deleteById(Integer appointmentId) {
		repo.deleteById(appointmentId);
	}

	@Override
	public Appointment findById(Integer appointmentId) {
		return repo.findById(appointmentId);
	}

	@Override
	public List<Appointment> findAll() {
		return repo.findAll();
	}

	@Override
	public List<Appointment> getAppointmentsByGroupId(int groupId) {
		return repo.getAppointmentsByGroupId(groupId);
	}

}
