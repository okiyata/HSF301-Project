package service.appointment;

import java.util.List;

import pojo.Appointment;

public interface AppointmentService {
	Appointment save(Appointment appointment);

	Appointment update(Appointment appointment);

	void delete(Appointment appointment);

	void deleteById(Integer appointmentId);

	Appointment findById(Integer appointmentId);

	List<Appointment> findAll();
	
	List<Appointment> getAppointmentsByGroupId(int groupId);
	
	List<Appointment> findFinishedAppointmentsWithoutGroupRating(int groupId);
	
	List<Appointment> findFinishedAppointmentsWithoutMentorRating(int mentorId);
}
