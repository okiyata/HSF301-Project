package dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import pojo.ProjectGroup;
import pojo.Student;

public class StudentDAO {
    private SessionFactory sessionFactory;
    private Session session;

    public StudentDAO() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    private Session getSession() {
        return sessionFactory.openSession();
    }

    public void save(Student student) {
        session = getSession();
        Transaction tr = session.beginTransaction();
        
        try {
            session.save(student);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public void update(Student student) {
        session = getSession();
        Transaction tr = session.beginTransaction();
        
        try {
            session.update(student);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public void delete(Student student) {
        session = getSession();
        Transaction tr = session.beginTransaction();
        
        try {
            session.delete(student);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            System.out.println(ex);
        } finally {
            session.close();
        }
    }

    public Student findById(int studentID) {
        session = getSession();
        Student student = null;
        
        try {
            student = session.get(Student.class, studentID);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            session.close();
        }
        
        return student;
    }
    
    public Student findByName(String studentName) {
        Session session = sessionFactory.openSession();
        Student student = null;

        try {
            Query<Student> query = session.createQuery("FROM Student WHERE studentName = :studentName", Student.class);
            query.setParameter("studentName", studentName);

            student = query.uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return student;
    }

    public List<Student> findAll() {
        session = getSession();
        List<Student> list = new LinkedList<>();
        
        try {
            list = session.createQuery("from Student", Student.class).getResultList();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            session.close();
        }
        
        return list;
    }
}
