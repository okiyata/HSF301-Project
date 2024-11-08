package dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import pojo.Appointment;

public class AppointmentDAO {
    private SessionFactory sessionFactory;
    private Session session;

    public AppointmentDAO() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    private Session getSession() {
        return sessionFactory.openSession();
    }

    public void save(Appointment appointment) {
        session = getSession();
        Transaction tr = session.beginTransaction();

        try {
            session.save(appointment);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public void update(Appointment appointment) {
        session = getSession();
        Transaction tr = session.beginTransaction();

        try {
            session.update(appointment);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public void delete(Appointment appointment) {
        session = getSession();
        Transaction tr = session.beginTransaction();

        try {
            session.delete(appointment);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public Appointment findById(int appointmentID) {
        session = getSession();
        Appointment appointment = null;

        try {
            appointment = session.get(Appointment.class, appointmentID);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            session.close();
        }

        return appointment;
    }

    public List<Appointment> findAll() {
        session = getSession();
        List<Appointment> list = new LinkedList<>();

        try {
            list = session.createQuery("from Appointment", Appointment.class).getResultList();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            session.close();
        }

        return list;
    }
    
    public List<Appointment> getAppointmentsByGroupId(int groupId) {
        Session session = sessionFactory.openSession();
        List<Appointment> appointments = null;

        try {
            String hql = "FROM Appointment a WHERE a.projectGroup.groupID = :groupId";
            Query<Appointment> query = session.createQuery(hql, Appointment.class);
            query.setParameter("groupId", groupId);
            appointments = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return appointments;
    }
    
    public List<Appointment> findFinishedAppointmentsWithoutGroupRating(int groupId) {
    	Session session = sessionFactory.openSession();
        List<Appointment> appointments = new ArrayList<>();

        try {
            String sql = "SELECT a.* " +
                         "FROM Appointment a " +
                         "LEFT JOIN Rating r ON a.appointmentID = r.appointment_id AND r.ratingType = 'GROUP' " +
                         "WHERE a.status = 'FINISHED' " +
                         "AND a.projectGroupID = :groupId " +
                         "AND r.ratingID IS NULL";

            Query<Appointment> query = session.createNativeQuery(sql, Appointment.class);
            query.setParameter("groupId", groupId);

            appointments = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return appointments;
    }
    
    public List<Appointment> findFinishedAppointmentsWithoutMentorRating(int mentorId) {
        Session session = sessionFactory.openSession();
        List<Appointment> appointments = new ArrayList<>();

        try {
            String sql = "SELECT a.* " +
                         "FROM Appointment a " +
                         "LEFT JOIN Rating r ON a.appointmentID = r.appointment_id AND r.ratingType = 'MENTOR' " +
                         "WHERE a.status = 'FINISHED' " +
                         "AND a.mentorID = :mentorId " +
                         "AND r.ratingID IS NULL";

            Query<Appointment> query = session.createNativeQuery(sql, Appointment.class);
            query.setParameter("mentorId", mentorId);

            appointments = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return appointments;
    }


}
