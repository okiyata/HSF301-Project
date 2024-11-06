package dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import pojo.Mentor;

public class MentorDAO {
    private SessionFactory sessionFactory;
    private Session session;

    public MentorDAO() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    private Session getSession() {
        return sessionFactory.openSession();
    }

    public void save(Mentor mentor) {
        session = getSession();
        Transaction tr = session.beginTransaction();
        
        try {
            session.save(mentor);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public void update(Mentor mentor) {
        session = getSession();
        Transaction tr = session.beginTransaction();
        
        try {
            session.update(mentor);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public void delete(Mentor mentor) {
        session = getSession();
        Transaction tr = session.beginTransaction();
        
        try {
            session.delete(mentor);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public Mentor findById(int mentorID) {
        session = getSession();
        Mentor mentor = null;
        
        try {
            mentor = session.get(Mentor.class, mentorID);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            session.close();
        }
        
        return mentor;
    }

    public List<Mentor> findAll() {
        session = getSession();
        List<Mentor> list = new LinkedList<>();
        
        try {
            list = session.createQuery("from Mentor", Mentor.class).getResultList();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            session.close();
        }
        
        return list;
    }
    
    public List<Mentor> getAvailableMentors() {
        Session session = sessionFactory.openSession();
        List<Mentor> mentors = null;

        try {
            String hql = "FROM Mentor m WHERE m.status = :status";
            Query<Mentor> query = session.createQuery(hql, Mentor.class);
            query.setParameter("status", "AVAILABLE");
            mentors = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return mentors;
    }
}
